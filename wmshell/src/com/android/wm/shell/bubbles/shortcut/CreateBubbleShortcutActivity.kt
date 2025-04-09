

package com.android.wm.shell.bubbles.shortcut

import android.app.Activity
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Bundle
import com.android.wm.shell.Flags
import com.android.wm.shell.R
import com.android.wm.shell.protolog.ShellProtoLogGroup.WM_SHELL_BUBBLES
import com.android.wm.shell.util.KtProtoLog

/** Activity to create a shortcut to open bubbles */
class CreateBubbleShortcutActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Flags.enableRetrievableBubbles()) {
            KtProtoLog.d(WM_SHELL_BUBBLES, "Creating a shortcut for bubbles")
            createShortcut()
        }
        finish()
    }

    private fun createShortcut() {
        val icon = Icon.createWithResource(this, R.drawable.ic_bubbles_shortcut_widget)
        // TODO(b/340337839): shortcut shows the sysui icon
        val shortcutInfo = BubbleShortcutHelper.createShortcut(this, icon)
        val shortcutManager = getSystemService(ShortcutManager::class.java)
        val shortcutIntent = shortcutManager?.createShortcutResultIntent(shortcutInfo)
        if (shortcutIntent != null) {
            setResult(RESULT_OK, shortcutIntent)
        } else {
            setResult(RESULT_CANCELED)
        }
    }
}
