

package com.android.systemui.surfaceeffects.ripple

import androidx.annotation.VisibleForTesting

/** Controller that handles playing [RippleAnimation]. */
class MultiRippleController(private val multipleRippleView: MultiRippleView) {

    companion object {
        /** Max number of ripple animations at a time. */
        @VisibleForTesting const val MAX_RIPPLE_NUMBER = 10
    }

    /** Updates all the ripple colors during the animation. */
    fun updateColor(color: Int) {
        multipleRippleView.ripples.forEach { anim -> anim.updateColor(color) }
    }

    fun play(rippleAnimation: RippleAnimation) {
        if (multipleRippleView.ripples.size >= MAX_RIPPLE_NUMBER) {
            return
        }

        multipleRippleView.ripples.add(rippleAnimation)

        rippleAnimation.play {
            // Remove ripple once the animation is done
            multipleRippleView.ripples.remove(rippleAnimation)
        }

        // Trigger drawing
        multipleRippleView.invalidate()
    }
}
