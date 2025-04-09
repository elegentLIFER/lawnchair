

package com.android.systemui.shared.shadow

import android.graphics.Canvas
import android.graphics.Color
import android.widget.TextView

object DoubleShadowTextHelper {
    data class ShadowInfo(
        val blur: Float,
        val offsetX: Float = 0f,
        val offsetY: Float = 0f,
        val alpha: Float
    )

    fun applyShadows(
        keyShadowInfo: ShadowInfo,
        ambientShadowInfo: ShadowInfo,
        view: TextView,
        canvas: Canvas,
        onDrawCallback: () -> Unit
    ) {
        // We enhance the shadow by drawing the shadow twice
        view.paint.setShadowLayer(
            ambientShadowInfo.blur,
            ambientShadowInfo.offsetX,
            ambientShadowInfo.offsetY,
            Color.argb(ambientShadowInfo.alpha, 0f, 0f, 0f)
        )
        onDrawCallback()
        canvas.save()
        canvas.clipRect(
            view.scrollX,
            view.scrollY + view.extendedPaddingTop,
            view.scrollX + view.width,
            view.scrollY + view.height
        )

        view.paint.setShadowLayer(
            keyShadowInfo.blur,
            keyShadowInfo.offsetX,
            keyShadowInfo.offsetY,
            Color.argb(keyShadowInfo.alpha, 0f, 0f, 0f)
        )
        onDrawCallback()
        canvas.restore()
    }
}
