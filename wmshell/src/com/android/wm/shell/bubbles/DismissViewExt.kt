
@file:JvmName("DismissViewUtils")

package com.android.wm.shell.bubbles

import com.android.wm.shell.R
import com.android.wm.shell.common.bubbles.DismissView

fun DismissView.setup() {
    setup(DismissView.Config(
            dismissViewResId = R.id.dismiss_view,
            targetSizeResId = R.dimen.dismiss_circle_size,
            iconSizeResId = R.dimen.dismiss_target_x_size,
            bottomMarginResId = R.dimen.floating_dismiss_bottom_margin,
            floatingGradientHeightResId = R.dimen.floating_dismiss_gradient_height,
            floatingGradientColorResId = android.R.color.system_neutral1_900,
            backgroundResId = R.drawable.dismiss_circle_background,
            iconResId = R.drawable.pip_ic_close_white
    ))
}
