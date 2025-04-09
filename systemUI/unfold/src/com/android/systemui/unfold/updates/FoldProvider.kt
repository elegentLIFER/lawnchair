
package com.android.systemui.unfold.updates

import java.util.concurrent.Executor

interface FoldProvider {
    fun registerCallback(callback: FoldCallback, executor: Executor)
    fun unregisterCallback(callback: FoldCallback)

    fun interface FoldCallback {
        fun onFoldUpdated(isFolded: Boolean)
    }
}
