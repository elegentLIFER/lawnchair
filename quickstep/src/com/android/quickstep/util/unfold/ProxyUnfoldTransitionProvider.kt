
package com.android.quickstep.util.unfold

import androidx.annotation.AnyThread
import androidx.annotation.FloatRange
import com.android.launcher3.util.Executors.MAIN_EXECUTOR
import com.android.systemui.unfold.UnfoldTransitionProgressProvider
import com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
import com.android.systemui.unfold.progress.IUnfoldTransitionListener
import com.android.systemui.unfold.progress.UnfoldRemoteFilter

/** Receives unfold events from remote senders (System UI). */
class ProxyUnfoldTransitionProvider :
    UnfoldTransitionProgressProvider, IUnfoldTransitionListener.Stub() {

    private val listeners: MutableSet<TransitionProgressListener> = mutableSetOf()
    private val delegate = UnfoldRemoteFilter(ProcessedProgressListener())

    private var transitionStarted = false
    var isActive = false
        set(value) {
            field = value
            if (!value) {
                // Finish any active transition
                onTransitionFinished()
            }
        }

    @AnyThread
    override fun onTransitionStarted() {
        MAIN_EXECUTOR.execute(delegate::onTransitionStarted)
    }

    @AnyThread
    override fun onTransitionProgress(progress: Float) {
        MAIN_EXECUTOR.execute { delegate.onTransitionProgress(progress) }
    }

    @AnyThread
    override fun onTransitionFinished() {
        MAIN_EXECUTOR.execute(delegate::onTransitionFinished)
    }

    override fun addCallback(listener: TransitionProgressListener) {
        listeners += listener
        if (transitionStarted) {
            // Update the listener in case there was is an active transition
            listener.onTransitionStarted()
        }
    }

    override fun removeCallback(listener: TransitionProgressListener) {
        listeners -= listener
        if (transitionStarted) {
            // Finish the transition if it was already running
            listener.onTransitionFinished()
        }
    }

    override fun destroy() {
        listeners.clear()
    }

    private inner class ProcessedProgressListener : TransitionProgressListener {
        override fun onTransitionStarted() {
            if (!transitionStarted) {
                transitionStarted = true
                listeners.forEach(TransitionProgressListener::onTransitionStarted)
            }
        }

        override fun onTransitionProgress(@FloatRange(from = 0.0, to = 1.0) progress: Float) {
            listeners.forEach { it.onTransitionProgress(progress) }
        }

        override fun onTransitionFinished() {
            if (transitionStarted) {
                transitionStarted = false
                listeners.forEach(TransitionProgressListener::onTransitionFinished)
            }
        }
    }
}
