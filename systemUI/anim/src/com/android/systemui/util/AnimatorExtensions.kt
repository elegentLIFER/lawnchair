

package com.android.systemui.util

import androidx.core.animation.Animator

/**
 * Add an action which will be invoked when the animation has ended.
 *
 * @return the [Animator.AnimatorListener] added to the Animator
 * @see Animator.end
 */
inline fun Animator.doOnEnd(
    crossinline action: (animator: Animator) -> Unit
): Animator.AnimatorListener = addListener(onEnd = action)

/**
 * Add an action which will be invoked when the animation has started.
 *
 * @return the [Animator.AnimatorListener] added to the Animator
 * @see Animator.start
 */
inline fun Animator.doOnStart(
    crossinline action: (animator: Animator) -> Unit
): Animator.AnimatorListener = addListener(onStart = action)

/**
 * Add an action which will be invoked when the animation has been cancelled.
 *
 * @return the [Animator.AnimatorListener] added to the Animator
 * @see Animator.cancel
 */
inline fun Animator.doOnCancel(
    crossinline action: (animator: Animator) -> Unit
): Animator.AnimatorListener = addListener(onCancel = action)

/**
 * Add an action which will be invoked when the animation has repeated.
 *
 * @return the [Animator.AnimatorListener] added to the Animator
 */
inline fun Animator.doOnRepeat(
    crossinline action: (animator: Animator) -> Unit
): Animator.AnimatorListener = addListener(onRepeat = action)

/**
 * Add a listener to this Animator using the provided actions.
 *
 * @return the [Animator.AnimatorListener] added to the Animator
 */
inline fun Animator.addListener(
    crossinline onEnd: (animator: Animator) -> Unit = {},
    crossinline onStart: (animator: Animator) -> Unit = {},
    crossinline onCancel: (animator: Animator) -> Unit = {},
    crossinline onRepeat: (animator: Animator) -> Unit = {}
): Animator.AnimatorListener {
    val listener =
        object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animator: Animator) = onRepeat(animator)
            override fun onAnimationEnd(animator: Animator) = onEnd(animator)
            override fun onAnimationCancel(animator: Animator) = onCancel(animator)
            override fun onAnimationStart(animator: Animator) = onStart(animator)
        }
    addListener(listener)
    return listener
}
