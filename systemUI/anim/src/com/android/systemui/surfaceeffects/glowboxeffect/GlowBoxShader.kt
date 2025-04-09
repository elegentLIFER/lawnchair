

package com.android.systemui.surfaceeffects.glowboxeffect

import android.graphics.RuntimeShader
import com.android.systemui.surfaceeffects.shaderutil.SdfShaderLibrary

/** Soft box shader. */
class GlowBoxShader : RuntimeShader(GLOW_SHADER) {
    // language=AGSL
    private companion object {
        private const val SHADER =
            """
            uniform half2 in_center;
            uniform half2 in_size;
            uniform half in_blur;
            layout(color) uniform half4 in_color;

            float4 main(float2 fragcoord) {
                half glow = soften(sdBox(fragcoord - in_center, in_size), in_blur);
                return in_color * (1. - glow);
            }
        """

        private const val GLOW_SHADER =
            SdfShaderLibrary.BOX_SDF + SdfShaderLibrary.SHADER_SDF_OPERATION_LIB + SHADER
    }

    fun setCenter(x: Float, y: Float) {
        setFloatUniform("in_center", x, y)
    }

    fun setSize(width: Float, height: Float) {
        setFloatUniform("in_size", width, height)
    }

    fun setBlur(blurAmount: Float) {
        setFloatUniform("in_blur", blurAmount)
    }

    fun setColor(color: Int) {
        setColorUniform("in_color", color)
    }
}
