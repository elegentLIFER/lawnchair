
package com.android.launcher3.taskbar

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.RenderEffect
import android.graphics.RenderNode
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.DrawableWrapper

/* BitmapDrawable that can blur the given bitmap. */
class BlurredBitmapDrawable(bitmap: Bitmap?, radiusX: Float, radiusY: Float) :
    DrawableWrapper(BitmapDrawable(bitmap)) {
    private val mBlurRenderNode: RenderNode = RenderNode("BlurredConstraintLayoutBlurNode")

    constructor(bitmap: Bitmap?, radius: Float) : this(bitmap, radius, radius)

    init {
        mBlurRenderNode.setRenderEffect(
            RenderEffect.createBlurEffect(radiusX, radiusY, Shader.TileMode.CLAMP)
        )
    }

    override fun draw(canvas: Canvas) {
        if (!canvas.isHardwareAccelerated) {
            super.draw(canvas)
            return
        }
        mBlurRenderNode.setPosition(bounds)
        if (!mBlurRenderNode.hasDisplayList()) {
            // Record render node if its display list is not recorded or discarded
            // (which happens when it's no longer drawn by anything).
            val recordingCanvas = mBlurRenderNode.beginRecording()
            super.draw(recordingCanvas)
            mBlurRenderNode.endRecording()
        }
        canvas.drawRenderNode(mBlurRenderNode)
    }

    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE
    }
}
