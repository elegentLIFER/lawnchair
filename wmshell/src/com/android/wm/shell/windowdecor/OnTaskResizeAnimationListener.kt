
package com.android.wm.shell.windowdecor

import android.graphics.Rect
import android.view.SurfaceControl

import com.android.wm.shell.transition.Transitions.TransitionHandler
/**
 * Listener that allows implementations of [TransitionHandler] to notify when an
 * animation that is resizing a task is starting, updating, and finishing the animation.
 */
interface OnTaskResizeAnimationListener {
    /**
     * Notifies that a transition animation is about to be started with the given bounds.
     */
    fun onAnimationStart(taskId: Int, t: SurfaceControl.Transaction, bounds: Rect)

    /**
     * Notifies that a transition animation is expanding or shrinking the task to the given bounds.
     */
    fun onBoundsChange(taskId: Int, t: SurfaceControl.Transaction, bounds: Rect)

    /**
     * Notifies that a transition animation is about to be finished.
     */
    fun onAnimationEnd(taskId: Int)
}
