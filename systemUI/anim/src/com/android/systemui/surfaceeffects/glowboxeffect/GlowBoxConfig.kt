

package com.android.systemui.surfaceeffects.glowboxeffect

/** Parameters used to play [GlowBoxEffect]. */
data class GlowBoxConfig(
    /** Start center position X in px. */
    val startCenterX: Float,
    /** Start center position Y in px. */
    val startCenterY: Float,
    /** End center position X in px. */
    val endCenterX: Float,
    /** End center position Y in px. */
    val endCenterY: Float,
    /** Width of the box in px. */
    val width: Float,
    /** Height of the box in px. */
    val height: Float,
    /** Color of the box in ARGB, Apply alpha value if needed. */
    val color: Int,
    /** Amount of blur (or glow) of the box. */
    val blurAmount: Float,
    /**
     * Duration of the animation. Note that the full duration of the animation is
     * [duration] + [easeInDuration] + [easeOutDuration].
     */
    val duration: Long,
    /** Ease in duration of the animation. */
    val easeInDuration: Long,
    /** Ease out duration of the animation. */
    val easeOutDuration: Long,
)
