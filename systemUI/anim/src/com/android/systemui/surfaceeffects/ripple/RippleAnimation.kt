

package com.android.systemui.surfaceeffects.ripple

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import androidx.annotation.VisibleForTesting
import androidx.core.graphics.ColorUtils

/** A single ripple animation. */
class RippleAnimation(private val config: RippleAnimationConfig) {
    @VisibleForTesting(otherwise = VisibleForTesting.PACKAGE_PRIVATE)
    val rippleShader: RippleShader = RippleShader(config.rippleShape)
    private val animator: ValueAnimator = ValueAnimator.ofFloat(0f, 1f)

    init {
        applyConfigToShader()
    }

    /** Updates the ripple color during the animation. */
    fun updateColor(color: Int) {
        config.apply { config.color = color }
        applyConfigToShader()
    }

    @JvmOverloads
    fun play(onAnimationEnd: Runnable? = null) {
        if (isPlaying()) {
            return // Ignore if ripple effect is already playing
        }

        animator.duration = config.duration
        animator.addUpdateListener { updateListener ->
            val now = updateListener.currentPlayTime
            val progress = updateListener.animatedValue as Float
            rippleShader.rawProgress = progress
            rippleShader.distortionStrength = if (config.shouldDistort) 1 - progress else 0f
            rippleShader.time = now.toFloat()
        }
        animator.addListener(
            object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    onAnimationEnd?.run()
                }
            }
        )
        animator.start()
    }

    /** Indicates whether the animation is playing. */
    fun isPlaying(): Boolean = animator.isRunning

    private fun applyConfigToShader() {
        with(rippleShader) {
            setCenter(config.centerX, config.centerY)
            rippleSize.setMaxSize(config.maxWidth, config.maxHeight)
            pixelDensity = config.pixelDensity
            color = ColorUtils.setAlphaComponent(config.color, config.opacity)
            sparkleStrength = config.sparkleStrength

            assignFadeParams(baseRingFadeParams, config.baseRingFadeParams)
            assignFadeParams(sparkleRingFadeParams, config.sparkleRingFadeParams)
            assignFadeParams(centerFillFadeParams, config.centerFillFadeParams)
        }
    }

    private fun assignFadeParams(
        destFadeParams: RippleShader.FadeParams,
        srcFadeParams: RippleShader.FadeParams?
    ) {
        srcFadeParams?.let {
            destFadeParams.fadeInStart = it.fadeInStart
            destFadeParams.fadeInEnd = it.fadeInEnd
            destFadeParams.fadeOutStart = it.fadeOutStart
            destFadeParams.fadeOutEnd = it.fadeOutEnd
        }
    }
}
