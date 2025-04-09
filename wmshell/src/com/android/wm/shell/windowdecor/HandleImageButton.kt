

package com.android.wm.shell.windowdecor

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.widget.ImageButton

/**
 * [ImageButton] for the handle at the top of fullscreen apps. Has custom hover
 * and press handling to grow the handle on hover enter and shrink the handle on
 * hover exit and press.
 */
class HandleImageButton (context: Context?, attrs: AttributeSet?) :
    ImageButton(context, attrs) {
    private val handleAnimator = ValueAnimator()

    override fun onHoverChanged(hovered: Boolean) {
        super.onHoverChanged(hovered)
        if (hovered) {
            animateHandle(HANDLE_HOVER_ANIM_DURATION, HANDLE_HOVER_ENTER_SCALE)
        } else {
            if (!isPressed) {
                animateHandle(HANDLE_HOVER_ANIM_DURATION, HANDLE_DEFAULT_SCALE)
            }
        }
    }

    override fun setPressed(pressed: Boolean) {
        if (isPressed != pressed) {
            super.setPressed(pressed)
            if (pressed) {
                animateHandle(HANDLE_PRESS_ANIM_DURATION, HANDLE_PRESS_DOWN_SCALE)
            } else {
                animateHandle(HANDLE_PRESS_ANIM_DURATION, HANDLE_DEFAULT_SCALE)
            }
        }
    }

    private fun animateHandle(duration: Long, endScale: Float) {
        if (handleAnimator.isRunning) {
            handleAnimator.cancel()
        }
        handleAnimator.duration = duration
        handleAnimator.setFloatValues(scaleX, endScale)
        handleAnimator.addUpdateListener { animator ->
            scaleX = animator.animatedValue as Float
        }
        handleAnimator.start()
    }

    companion object {
        /** The duration of animations related to hover state. **/
        private const val HANDLE_HOVER_ANIM_DURATION = 300L
        /** The duration of animations related to pressed state. **/
        private const val HANDLE_PRESS_ANIM_DURATION = 200L
        /** Ending scale for hover enter. **/
        private const val HANDLE_HOVER_ENTER_SCALE = 1.2f
        /** Ending scale for press down. **/
        private const val HANDLE_PRESS_DOWN_SCALE = 0.85f
        /** Default scale for handle. **/
        private const val HANDLE_DEFAULT_SCALE = 1f
    }
}
