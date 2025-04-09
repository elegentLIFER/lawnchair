
package com.android.launcher3.taskbar.bubbles

import android.graphics.Bitmap
import android.graphics.Path
import com.android.wm.shell.common.bubbles.BubbleInfo

/** An entity in the bubble bar. */
sealed class BubbleBarItem(open var key: String, open var view: BubbleView)

/** Contains state info about a bubble in the bubble bar as well as presentation information. */
data class BubbleBarBubble(
    var info: BubbleInfo,
    override var view: BubbleView,
    var badge: Bitmap,
    var icon: Bitmap,
    var dotColor: Int,
    var dotPath: Path,
    var appName: String
) : BubbleBarItem(info.key, view)

/** Represents the overflow bubble in the bubble bar. */
data class BubbleBarOverflow(override var view: BubbleView) : BubbleBarItem("Overflow", view)
