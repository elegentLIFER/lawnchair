
package com.android.systemui.unfold.progress

import android.os.Looper
import android.view.Choreographer
import androidx.dynamicanimation.animation.FrameCallbackScheduler
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

/**
 * Scheduler that posts animation progresses on a thread different than the ui one.
 *
 * The following is taken from [AnimationHandler.FrameCallbackScheduler16]. It is extracted here as
 * there are no guarantees which implementation the [DynamicAnimation] class would use otherwise.
 * This allows classes using [DynamicAnimation] to be created in any thread, but still use the
 * scheduler for a specific thread.
 *
 * Technically the [AssistedInject] is not needed: it's just to have a nicer factory with a
 * documentation snippet instead of using a plain dagger provider.
 */
class UnfoldFrameCallbackScheduler @AssistedInject constructor() : FrameCallbackScheduler {

    private val choreographer = Choreographer.getInstance()
    private val looper =
        Looper.myLooper() ?: error("This should be created in a thread with a looper.")

    override fun postFrameCallback(frameCallback: Runnable) {
        choreographer.postFrameCallback { frameCallback.run() }
    }

    override fun isCurrentThread(): Boolean {
        return looper.isCurrentThread
    }

    @AssistedFactory
    interface Factory {
        /**
         * Creates a [FrameCallbackScheduler] that uses [Choreographer] to post frame callbacks.
         *
         * Note that the choreographer used depends on the thread this [create] is called on, as it
         * is get from a thread static attribute.
         */
        fun create(): UnfoldFrameCallbackScheduler
    }
}
