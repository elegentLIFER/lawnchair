

package com.android.systemui.surfaceeffects.utils

/** Copied from android.utils.MathUtils */
object MathUtils {
    fun lerp(start: Float, stop: Float, amount: Float): Float {
        return start + (stop - start) * amount
    }
}
