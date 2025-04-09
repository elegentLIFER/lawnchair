

package com.android.systemui.surfaceeffects.ripple

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.VisibleForTesting

/**
 * A view that allows multiple ripples to play.
 *
 * Use [MultiRippleController] to play ripple animations.
 */
class MultiRippleView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    @VisibleForTesting(otherwise = VisibleForTesting.PACKAGE_PRIVATE)
    val ripples = ArrayList<RippleAnimation>()
    private val ripplePaint = Paint()

    companion object {
        private const val TAG = "MultiRippleView"
    }

    override fun onDraw(canvas: Canvas) {
        if (!canvas.isHardwareAccelerated) {
            // Drawing with the ripple shader requires hardware acceleration, so skip if it's
            // unsupported.
            return
        }

        var shouldInvalidate = false

        ripples.forEach { anim ->
            ripplePaint.shader = anim.rippleShader
            canvas.drawPaint(ripplePaint)

            shouldInvalidate = shouldInvalidate || anim.isPlaying()
        }

        if (shouldInvalidate) {
            invalidate()
        }
    }
}
