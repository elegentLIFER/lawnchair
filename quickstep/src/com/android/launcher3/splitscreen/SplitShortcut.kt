

package com.android.launcher3.splitscreen

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.view.View
import com.android.launcher3.model.data.ItemInfo
import com.android.launcher3.model.data.WorkspaceItemInfo
import com.android.launcher3.popup.QuickstepSystemShortcut
import com.android.launcher3.popup.SystemShortcut
import com.android.launcher3.util.SplitConfigurationOptions
import com.android.launcher3.util.SplitConfigurationOptions.SplitPositionOption
import com.android.launcher3.util.SplitConfigurationOptions.SplitSelectSource
import com.android.launcher3.views.ActivityContext

/**
 * Shortcut to allow starting split. Default interaction for [onClick] is to launch split selection
 * mode
 */
abstract class SplitShortcut<T>(
    iconResId: Int,
    labelResId: Int,
    target: T,
    itemInfo: ItemInfo?,
    originalView: View?,
    protected val position: SplitPositionOption
) : SystemShortcut<T>(iconResId, labelResId, target, itemInfo, originalView) where
T : Context?,
T : ActivityContext? {
    private val TAG = "SplitShortcut"

    // Initiate splitscreen from the Home screen or Home All Apps
    protected val splitSelectSource: SplitSelectSource?
        get() {
            // Initiate splitscreen from the Home screen or Home All Apps
            val bitmap: Bitmap
            val intent: Intent
            when (mItemInfo) {
                is WorkspaceItemInfo -> {
                    val workspaceItemInfo = mItemInfo
                    bitmap = workspaceItemInfo.bitmap.icon
                    intent = workspaceItemInfo.intent
                }
                is com.android.launcher3.model.data.AppInfo -> {
                    val appInfo = mItemInfo
                    bitmap = appInfo.bitmap.icon
                    intent = appInfo.intent
                }
                else -> {
                    Log.e(TAG, "unknown item type")
                    return null
                }
            }
            val splitEvent =
                SplitConfigurationOptions.getLogEventForPosition(position.stagePosition)
            return SplitSelectSource(
                mOriginalView,
                BitmapDrawable(bitmap),
                intent,
                position,
                mItemInfo,
                splitEvent
            )
        }

    /** Starts split selection on the provided [mTarget] */
    override fun onClick(view: View?) {
        val splitSelectSource = splitSelectSource
        if (splitSelectSource == null) {
            Log.w(QuickstepSystemShortcut.TAG, "no split selection source")
            return
        }
        mTarget!!.startSplitSelection(splitSelectSource)
    }
}
