

package com.android.systemui.surfaceeffects

import android.graphics.RenderEffect

/**
 * A callback with a [RenderEffect] object that contains shader info, which is triggered every frame
 * while animation is playing. Note that the [RenderEffect] instance is different each time to
 * update shader uniforms.
 *
 * The usage of this callback is as follows:
 * <pre>{@code
 *     private val xEffectDrawingCallback = RenderEffectDrawCallback() {
 *         val myOtherRenderEffect = createOtherRenderEffect()
 *         val chainEffect = RenderEffect.createChainEffect(renderEffect, myOtherRenderEffect)
 *         myView.setRenderEffect(chainEffect)
 *     }
 *
 *     private val xEffect = XEffect(config, xEffectDrawingCallback)
 * }</pre>
 */
interface RenderEffectDrawCallback {
    fun onDraw(renderEffect: RenderEffect)
}
