
package com.android.systemui.unfold.compat

import com.android.systemui.unfold.updates.FoldProvider
import com.android.systemui.unfold.updates.screen.ScreenStatusProvider
import com.android.systemui.unfold.updates.screen.ScreenStatusProvider.ScreenListener
import java.util.concurrent.Executor

class SizeScreenStatusProvider(
    private val foldProvider: FoldProvider,
    private val executor: Executor
) : ScreenStatusProvider {

    private val listeners: MutableList<ScreenListener> = arrayListOf()
    private val callback = object : FoldProvider.FoldCallback {
        override fun onFoldUpdated(isFolded: Boolean) {
            if (!isFolded) {
                listeners.forEach { it.onScreenTurnedOn() }
            }
        }
    }

    fun start() {
        foldProvider.registerCallback(
            callback,
            executor
        )
    }

    fun stop() {
        foldProvider.unregisterCallback(callback)
    }

    override fun addCallback(listener: ScreenListener) {
        listeners.add(listener)
    }

    override fun removeCallback(listener: ScreenListener) {
        listeners.remove(listener)
    }
}
