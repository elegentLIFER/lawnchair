
package com.android.systemui.surfaceeffects.shaders

import android.graphics.RuntimeShader

/** Simply renders a solid color. */
class SolidColorShader(color: Int) : RuntimeShader(SHADER) {
    // language=AGSL
    private companion object {
        private const val SHADER =
            """
                layout(color) uniform vec4 in_color;
                vec4 main(vec2 p) {
                    return in_color;
                }
            """
    }

    init {
        setColorUniform("in_color", color)
    }
}
