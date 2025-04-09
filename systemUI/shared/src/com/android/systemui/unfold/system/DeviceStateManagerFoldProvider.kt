
package com.android.systemui.unfold.system

import android.content.Context
import android.hardware.devicestate.DeviceStateManager
import com.android.systemui.unfold.updates.FoldProvider
import com.android.systemui.unfold.updates.FoldProvider.FoldCallback
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceStateManagerFoldProvider
@Inject
constructor(private val deviceStateManager: DeviceStateManager, private val context: Context) :
    FoldProvider {

    private val callbacks =
        ConcurrentHashMap<FoldCallback, DeviceStateManager.DeviceStateCallback>()

    override fun registerCallback(callback: FoldCallback, executor: Executor) {
        val listener = FoldStateListener(context, callback)
        deviceStateManager.registerCallback(executor, listener)
        callbacks[callback] = listener
    }

    override fun unregisterCallback(callback: FoldCallback) {
        val listener = callbacks.remove(callback)
        listener?.let { deviceStateManager.unregisterCallback(it) }
    }

    private inner class FoldStateListener(context: Context, listener: FoldCallback) :
        DeviceStateManager.FoldStateListener(context, { listener.onFoldUpdated(it) })
}
