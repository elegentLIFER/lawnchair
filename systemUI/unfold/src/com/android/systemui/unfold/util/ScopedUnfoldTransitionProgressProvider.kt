
package com.android.systemui.unfold.util

import android.os.Handler
import android.os.Looper
import androidx.annotation.GuardedBy
import com.android.systemui.unfold.UnfoldTransitionProgressProvider
import com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Manages progress listeners that can have smaller lifespan than the unfold animation.
 *
 * Allows to limit getting transition updates to only when
 * [ScopedUnfoldTransitionProgressProvider.setReadyToHandleTransition] is called with
 * readyToHandleTransition = true
 *
 * If the transition has already started by the moment when the clients are ready to play the
 * transition then it will report transition started callback and current animation progress.
 */
open class ScopedUnfoldTransitionProgressProvider
@JvmOverloads
constructor(source: UnfoldTransitionProgressProvider? = null) :
    UnfoldTransitionProgressProvider, TransitionProgressListener {

    private var progressHandler: Handler? = null
    private var source: UnfoldTransitionProgressProvider? = null

    private val listeners = CopyOnWriteArrayList<TransitionProgressListener>()

    private val lock = Object()

    @GuardedBy("lock") private var isReadyToHandleTransition = false
    // Accessed only from progress thread
    private var isTransitionRunning = false
    // Accessed only from progress thread
    private var lastTransitionProgress = PROGRESS_UNSET

    init {
        setSourceProvider(source)
    }
    /**
     * Sets the source for the unfold transition progress updates. Replaces current provider if it
     * is already set
     *
     * @param provider transition provider that emits transition progress updates
     */
    fun setSourceProvider(provider: UnfoldTransitionProgressProvider?) {
        source?.removeCallback(this)

        if (provider != null) {
            source = provider
            provider.addCallback(this)
        } else {
            source = null
        }
    }

    /**
     * Allows to notify this provide whether the listeners can play the transition or not.
     *
     * Call this method with readyToHandleTransition = true when all listeners are ready to consume
     * the transition progress events.
     *
     * Call it with readyToHandleTransition = false when listeners can't process the events.
     *
     * Note that this could be called by any thread.
     */
    fun setReadyToHandleTransition(isReadyToHandleTransition: Boolean) {
        synchronized(lock) {
            this.isReadyToHandleTransition = isReadyToHandleTransition
            val progressHandlerLocal = this.progressHandler
            if (progressHandlerLocal != null) {
                ensureInHandler(progressHandlerLocal) { reportLastProgressIfNeeded() }
            }
        }
    }

    /** Runs directly if called from the handler thread. Posts otherwise. */
    private fun ensureInHandler(handler: Handler, f: () -> Unit) {
        if (handler.looper.isCurrentThread) {
            f()
        } else {
            handler.post(f)
        }
    }

    private fun reportLastProgressIfNeeded() {
        assertInProgressThread()
        synchronized(lock) {
            if (!isTransitionRunning) {
                return
            }
            if (isReadyToHandleTransition) {
                listeners.forEach { it.onTransitionStarted() }
                if (lastTransitionProgress != PROGRESS_UNSET) {
                    listeners.forEach { it.onTransitionProgress(lastTransitionProgress) }
                }
            } else {
                isTransitionRunning = false
                listeners.forEach { it.onTransitionFinished() }
            }
        }
    }

    override fun addCallback(listener: TransitionProgressListener) {
        listeners += listener
    }

    override fun removeCallback(listener: TransitionProgressListener) {
        listeners -= listener
    }

    override fun destroy() {
        source?.removeCallback(this)
        source?.destroy()
    }

    override fun onTransitionStarted() {
        assertInProgressThread()
        synchronized(lock) {
            isTransitionRunning = true
            if (isReadyToHandleTransition) {
                listeners.forEach { it.onTransitionStarted() }
            }
        }
    }

    override fun onTransitionProgress(progress: Float) {
        assertInProgressThread()
        synchronized(lock) {
            if (isReadyToHandleTransition) {
                listeners.forEach { it.onTransitionProgress(progress) }
            }
            lastTransitionProgress = progress
        }
    }

    override fun onTransitionFinishing() {
        assertInProgressThread()
        synchronized(lock) {
            if (isReadyToHandleTransition) {
                listeners.forEach { it.onTransitionFinishing() }
            }
        }
    }

    override fun onTransitionFinished() {
        assertInProgressThread()
        synchronized(lock) {
            if (isReadyToHandleTransition) {
                listeners.forEach { it.onTransitionFinished() }
            }
            isTransitionRunning = false
            lastTransitionProgress = PROGRESS_UNSET
        }
    }

    private fun assertInProgressThread() {
        val cachedProgressHandler = progressHandler
        if (cachedProgressHandler == null) {
            val thisLooper = Looper.myLooper() ?: error("This thread is expected to have a looper.")
            progressHandler = Handler(thisLooper)
        } else {
            check(cachedProgressHandler.looper.isCurrentThread) {
                """Receiving unfold transition callback from different threads.
                    |Current: ${Thread.currentThread()}
                    |expected: ${cachedProgressHandler.looper.thread}"""
                    .trimMargin()
            }
        }
    }

    private companion object {
        const val PROGRESS_UNSET = -1f
    }
}
