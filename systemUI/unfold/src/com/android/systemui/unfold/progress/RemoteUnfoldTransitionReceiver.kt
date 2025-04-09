

package com.android.systemui.unfold.progress

import android.util.Log
import androidx.annotation.BinderThread
import androidx.annotation.FloatRange
import com.android.systemui.unfold.UnfoldTransitionProgressProvider
import com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
import com.android.systemui.unfold.dagger.UnfoldMain
import com.android.systemui.unfold.dagger.UseReceivingFilter
import java.util.concurrent.Executor
import javax.inject.Inject

/**
 * Receives unfold events from remote senders (System UI).
 *
 * A binder to an instance to this class (created with [RemoteUnfoldTransitionReceiver.asBinder])
 * should be sent to the remote process providing events.
 */
class RemoteUnfoldTransitionReceiver
@Inject
constructor(
    @UseReceivingFilter useReceivingFilter: Boolean,
    @UnfoldMain private val executor: Executor
) : UnfoldTransitionProgressProvider, IUnfoldTransitionListener.Stub() {

    private val listeners: MutableSet<TransitionProgressListener> = mutableSetOf()
    private val outputProgressListener = ProcessedProgressListener()
    private val filter: TransitionProgressListener? =
        if (useReceivingFilter) {
            UnfoldRemoteFilter(outputProgressListener)
        } else {
            null
        }

    @BinderThread
    override fun onTransitionStarted() {
        executor.execute {
            filter?.onTransitionStarted() ?: outputProgressListener.onTransitionStarted()
        }
    }

    @BinderThread
    override fun onTransitionProgress(progress: Float) {
        executor.execute {
            filter?.onTransitionProgress(progress)
                ?: outputProgressListener.onTransitionProgress(progress)
        }
    }

    @BinderThread
    override fun onTransitionFinished() {
        executor.execute {
            filter?.onTransitionFinished() ?: outputProgressListener.onTransitionFinished()
        }
    }

    override fun addCallback(listener: TransitionProgressListener) {
        listeners += listener
    }

    override fun removeCallback(listener: TransitionProgressListener) {
        listeners -= listener
    }

    override fun destroy() {
        listeners.clear()
    }

    private inner class ProcessedProgressListener : TransitionProgressListener {
        override fun onTransitionStarted() {
            log { "onTransitionStarted" }
            listeners.forEach { it.onTransitionStarted() }
        }

        override fun onTransitionProgress(@FloatRange(from = 0.0, to = 1.0) progress: Float) {
            log { "onTransitionProgress" }
            listeners.forEach { it.onTransitionProgress(progress) }
        }

        override fun onTransitionFinished() {
            log { "onTransitionFinished" }
            listeners.forEach { it.onTransitionFinished() }
        }
    }

    private fun log(s: () -> String) {
        if (DEBUG) {
            Log.d(TAG, s())
        }
    }
}

private const val TAG = "RemoteUnfoldReceiver"
private val DEBUG = false
