

package com.android.systemui.animation.back

import android.util.DisplayMetrics
import android.view.animation.Interpolator
import com.android.app.animation.Interpolators
import com.android.systemui.util.dpToPx

private const val MAX_SCALE_DELTA_DP = 48

/** Create a [BackAnimationSpec] from [displayMetrics] and design specs. */
fun BackAnimationSpec.Companion.createBottomsheetAnimationSpec(
    displayMetricsProvider: () -> DisplayMetrics,
    scaleEasing: Interpolator = Interpolators.BACK_GESTURE,
): BackAnimationSpec {
    return BackAnimationSpec { backEvent, _, result ->
        val displayMetrics = displayMetricsProvider()
        val screenWidthPx = displayMetrics.widthPixels
        val minScale = 1 - MAX_SCALE_DELTA_DP.dpToPx(displayMetrics) / screenWidthPx
        val progressX = backEvent.progress
        val ratioScale = scaleEasing.getInterpolation(progressX)
        result.apply {
            scale = 1f - ratioScale * (1f - minScale)
            scalePivotPosition = ScalePivotPosition.BOTTOM_CENTER
        }
    }
}
