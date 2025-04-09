
package com.android.wm.shell.bubbles

import android.graphics.Color
import com.android.wm.shell.R
import com.android.wm.shell.common.bubbles.BubblePopupDrawable
import com.android.wm.shell.common.bubbles.BubblePopupView

/**
 * A convenience method to setup the [BubblePopupView] with the correct config using local resources
 */
fun BubblePopupView.setup() {
    val attrs =
        context.obtainStyledAttributes(
            intArrayOf(
                com.android.internal.R.attr.materialColorSurface,
                android.R.attr.dialogCornerRadius
            )
        )

    val res = context.resources
    val config =
        BubblePopupDrawable.Config(
            color = attrs.getColor(0, Color.WHITE),
            cornerRadius = attrs.getDimension(1, 0f),
            contentPadding = res.getDimensionPixelSize(R.dimen.bubble_popup_padding),
            arrowWidth = res.getDimension(R.dimen.bubble_popup_arrow_width),
            arrowHeight = res.getDimension(R.dimen.bubble_popup_arrow_height),
            arrowRadius = res.getDimension(R.dimen.bubble_popup_arrow_corner_radius)
        )
    attrs.recycle()
    setupBackground(config)
}
