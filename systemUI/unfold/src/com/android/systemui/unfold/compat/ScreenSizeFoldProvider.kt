
package com.android.systemui.unfold.compat

import android.content.Context
import android.content.res.Configuration
import com.android.systemui.unfold.updates.FoldProvider
import com.android.systemui.unfold.updates.FoldProvider.FoldCallback
import java.util.concurrent.Executor

/**
 * Fold provider that notifies about fold state based on the screen size
 * It could be used when no activity context is available
 * TODO(b/232369816): use Jetpack WM library when non-activity contexts supported b/169740873
 */
class ScreenSizeFoldProvider(context: Context) : FoldProvider {

    private var isFolded: Boolean = false
    private var callbacks: MutableList<FoldCallback> = arrayListOf()

    init {
        onConfigurationChange(context.resources.configuration)
    }

    override fun registerCallback(callback: FoldCallback, executor: Executor) {
        callbacks += callback
        callback.onFoldUpdated(isFolded)
    }

    override fun unregisterCallback(callback: FoldCallback) {
        callbacks -= callback
    }

    fun onConfigurationChange(newConfig: Configuration) {
        val newIsFolded =
                newConfig.smallestScreenWidthDp < INNER_SCREEN_SMALLEST_SCREEN_WIDTH_THRESHOLD_DP
        if (newIsFolded == isFolded) {
            return
        }

        isFolded = newIsFolded
        callbacks.forEach { it.onFoldUpdated(isFolded) }
    }
}

internal const val INNER_SCREEN_SMALLEST_SCREEN_WIDTH_THRESHOLD_DP = 600
