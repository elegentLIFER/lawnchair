
package com.android.systemui.unfold.system

import com.android.systemui.unfold.dagger.UnfoldMain
import com.android.systemui.unfold.updates.FoldProvider
import com.android.systemui.unfold.updates.FoldProvider.FoldCallback
import java.util.concurrent.Executor
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.callbackFlow

/** Provides whether the device is folded. */
interface DeviceStateRepository {
    val isFolded: Flow<Boolean>
}

@Singleton
class DeviceStateRepositoryImpl
@Inject
constructor(
    private val foldProvider: FoldProvider,
    @UnfoldMain private val executor: Executor,
) : DeviceStateRepository {

    override val isFolded: Flow<Boolean>
        get() =
            callbackFlow {
                    val callback = FoldCallback { isFolded -> trySend(isFolded) }
                    foldProvider.registerCallback(callback, executor)
                    awaitClose { foldProvider.unregisterCallback(callback) }
                }
                .buffer(capacity = Channel.CONFLATED)
}
