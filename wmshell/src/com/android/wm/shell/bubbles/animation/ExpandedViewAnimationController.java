
package com.android.wm.shell.bubbles.animation;

import android.graphics.PointF;

import com.android.wm.shell.bubbles.BubbleExpandedView;

/**
 * Animation controller for bubble expanded view collapsing
 */
public interface ExpandedViewAnimationController {
    /**
     * Set expanded view that this controller is working with.
     */
    void setExpandedView(BubbleExpandedView expandedView);

    /**
     * Set current collapse value, in pixels.
     *
     * @param distance pixels that user dragged the view by
     */
    void updateDrag(float distance);

    /**
     * Set current swipe velocity.
     * Velocity is directional:
     * <ul>
     *     <li>velocity < 0 means swipe direction is up</li>
     *     <li>velocity > 0 means swipe direction is down</li>
     * </ul>
     */
    void setSwipeVelocity(float velocity);

    /**
     * Check if view is dragged past collapse threshold or swipe up velocity exceeds min velocity
     * required to collapse the view
     */
    boolean shouldCollapse();

    /**
     * Animate view to collapsed state
     *
     * @param startStackCollapse runnable that is triggered when bubbles can start moving back to
     *                           their collapsed location
     * @param after              runnable to run after animation is complete
     * @param collapsePosition the position on screen the stack will collapse to
     */
    void animateCollapse(Runnable startStackCollapse, Runnable after, PointF collapsePosition);

    /**
     * Animate the view back to fully expanded state.
     */
    void animateBackToExpanded();

    /**
     * Animate view for IME visibility change
     */
    void animateForImeVisibilityChange(boolean visible);

    /**
     * Whether this controller should also animate the expansion for the bubble
     */
    boolean shouldAnimateExpansion();

    /**
     * Animate the expansion of the bubble.
     *
     * @param startDelayMillis how long to delay starting the expansion animation
     * @param after runnable to run after the animation is complete
     * @param collapsePosition the position on screen the stack will collapse to (and expand from)
     * @param bubblePosition the position of the bubble on screen that the view is associated with
     */
    void animateExpansion(long startDelayMillis, Runnable after, PointF collapsePosition,
            PointF bubblePosition);

    /**
     * Reset the view to fully expanded state
     */
    void reset();
}
