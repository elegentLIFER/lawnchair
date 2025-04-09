

package com.android.wm.shell.bubbles.shortcut

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.android.wm.shell.Flags
import com.android.wm.shell.protolog.ShellProtoLogGroup.WM_SHELL_BUBBLES
import com.android.wm.shell.util.KtProtoLog

/** Activity that sends a broadcast to open bubbles */
class ShowBubblesActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Flags.enableRetrievableBubbles()) {
            val intent =
                Intent().apply {
                    action = BubbleShortcutHelper.ACTION_SHOW_BUBBLES
                    // Set the package as the receiver is not exported
                    `package` = packageName
                }
            KtProtoLog.v(WM_SHELL_BUBBLES, "Sending broadcast to show bubbles")
            sendBroadcast(intent)
        }
        finish()
    }

    companion object {
        /** Create intent to launch this activity */
        fun createIntent(context: Context): Intent {
            return Intent(context, ShowBubblesActivity::class.java).apply {
                action = BubbleShortcutHelper.ACTION_SHOW_BUBBLES
            }
        }

        /** Create component for this activity */
        fun createComponent(context: Context): ComponentName {
            return ComponentName(context, ShowBubblesActivity::class.java)
        }
    }
}
