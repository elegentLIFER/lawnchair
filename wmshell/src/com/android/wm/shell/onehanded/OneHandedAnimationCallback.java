

package com.android.wm.shell.onehanded;

import android.view.SurfaceControl;

/**
 * Additional callback interface for OneHanded animation
 */
public interface OneHandedAnimationCallback {
    /**
     * Called when OneHanded animation is started.
     */
    default void onOneHandedAnimationStart(
            OneHandedAnimationController.OneHandedTransitionAnimator animator) {
    }

    /**
     * Called when OneHanded animation is ended.
     */
    default void onOneHandedAnimationEnd(SurfaceControl.Transaction tx,
            OneHandedAnimationController.OneHandedTransitionAnimator animator) {
    }

    /**
     * Called when OneHanded animation is cancelled.
     */
    default void onOneHandedAnimationCancel(
            OneHandedAnimationController.OneHandedTransitionAnimator animator) {
    }

    /**
     * Called when OneHanded animator is updating position
     */
    default void onAnimationUpdate(SurfaceControl.Transaction tx, float xPos, float yPos) {
    }

}
