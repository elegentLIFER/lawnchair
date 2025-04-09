

package com.android.systemui.unfold.progress

import android.os.Handler
import androidx.annotation.FloatRange
import com.android.systemui.unfold.UnfoldTransitionProgressProvider
import com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
import com.android.systemui.unfold.dagger.UnfoldMain
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.util.Collections.synchronizedMap

/**
 * [UnfoldTransitionProgressProvider] that forwards all progress to the main thread handler.
 *
 * This is needed when progress are calculated in the background, but some listeners need the
 * callbacks in the main thread.
 *
 * Note that this class assumes that the root provider has thread safe callback registration, as
 * they might be called from any thread.
 */
class MainThreadUnfoldTransitionProgressProvider
@AssistedInject
constructor(
    @UnfoldMain private val mainHandler: Handler,
    @Assisted private val rootProvider: UnfoldTransitionProgressProvider
) : UnfoldTransitionProgressProvider {

    private val listenerMap: MutableMap<TransitionProgressListener, TransitionProgressListener> =
        synchronizedMap(mutableMapOf())

    override fun addCallback(listener: TransitionProgressListener) {
        val proxy = TransitionProgressListerProxy(listener)
        rootProvider.addCallback(proxy)
        listenerMap[listener] = proxy
    }

    override fun removeCallback(listener: TransitionProgressListener) {
        val proxy = listenerMap.remove(listener) ?: return
        rootProvider.removeCallback(proxy)
    }

    override fun destroy() {
        rootProvider.destroy()
    }

    inner class TransitionProgressListerProxy(private val listener: TransitionProgressListener) :
        TransitionProgressListener {
        override fun onTransitionStarted() {
            mainHandler.post { listener.onTransitionStarted() }
        }

        override fun onTransitionProgress(@FloatRange(from = 0.0, to = 1.0) progress: Float) {
            mainHandler.post { listener.onTransitionProgress(progress) }
        }

        override fun onTransitionFinishing() {
            mainHandler.post { listener.onTransitionFinishing() }
        }

        override fun onTransitionFinished() {
            mainHandler.post { listener.onTransitionFinished() }
        }
    }

    @AssistedFactory
    interface Factory {
        /** Creates a [MainThreadUnfoldTransitionProgressProvider] that wraps the [rootProvider]. */
        fun create(
            rootProvider: UnfoldTransitionProgressProvider
        ): MainThreadUnfoldTransitionProgressProvider
    }
}
