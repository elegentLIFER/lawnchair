

package com.android.systemui.surfaceeffects

import android.graphics.Paint
import android.graphics.RenderEffect

/**
 * A callback with a [Paint] object that contains shader info, which is triggered every frame while
 * animation is playing. Note that the [Paint] object here is always the same instance.
 *
 * This approach is more performant than other ones because [RenderEffect] forces an intermediate
 * render pass of the View to a texture to feed into it.
 *
 * The usage of this callback is as follows:
 * <pre>{@code
 *     private var paint: Paint? = null
 *     // Override [View.onDraw].
 *     override fun onDraw(canvas: Canvas) {
 *         // RuntimeShader requires hardwareAcceleration.
 *         if (!canvas.isHardwareAccelerated) return
 *
 *         paint?.let { canvas.drawPaint(it) }
 *     }
 *
 *     // Given that this is called [PaintDrawCallback.onDraw]
 *     fun draw(paint: Paint) {
 *         this.paint = paint
 *
 *         // Must call invalidate to trigger View#onDraw
 *         invalidate()
 *     }
 * }</pre>
 *
 * Please refer to [RenderEffectDrawCallback] for alternative approach.
 */
interface PaintDrawCallback {
    fun onDraw(paint: Paint)
}
