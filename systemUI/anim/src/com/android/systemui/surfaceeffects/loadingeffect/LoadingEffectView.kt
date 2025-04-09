

package com.android.systemui.surfaceeffects.loadingeffect

import android.content.Context
import android.graphics.BlendMode
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/** Custom View for drawing the [LoadingEffect] with [Canvas.drawPaint]. */
open class LoadingEffectView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var paint: Paint? = null
    private var blendMode: BlendMode = BlendMode.SRC_OVER

    override fun onDraw(canvas: Canvas) {
        if (!canvas.isHardwareAccelerated) {
            return
        }
        paint?.let { canvas.drawPaint(it) }
    }

    /** Designed to be called on [LoadingEffect.PaintDrawCallback.onDraw]. */
    fun draw(paint: Paint) {
        this.paint = paint
        this.paint!!.blendMode = blendMode

        invalidate()
    }

    /** Sets the blend mode of the [Paint]. */
    fun setBlendMode(blendMode: BlendMode) {
        this.blendMode = blendMode
    }
}
