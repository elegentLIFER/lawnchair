

package com.android.wm.shell.bubbles.shortcut

import android.content.Context
import android.content.pm.ShortcutInfo
import android.graphics.drawable.Icon
import com.android.wm.shell.R

/** Helper class for creating a shortcut to open bubbles */
object BubbleShortcutHelper {
    const val SHORTCUT_ID = "bubbles_shortcut_id"
    const val ACTION_SHOW_BUBBLES = "com.android.wm.shell.bubbles.action.SHOW_BUBBLES"

    /** Create a shortcut that launches [ShowBubblesActivity] */
    fun createShortcut(context: Context, icon: Icon): ShortcutInfo {
        return ShortcutInfo.Builder(context, SHORTCUT_ID)
            .setIntent(ShowBubblesActivity.createIntent(context))
            .setActivity(ShowBubblesActivity.createComponent(context))
            .setShortLabel(context.getString(R.string.bubble_shortcut_label))
            .setLongLabel(context.getString(R.string.bubble_shortcut_long_label))
            .setLongLived(true)
            .setIcon(icon)
            .build()
    }
}
