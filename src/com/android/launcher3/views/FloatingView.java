
package com.android.launcher3.views;

/**
 * Shared interface for floating views.
 */
public interface FloatingView {

    /**
     * Offsets and updates the position of this view by {@param y}.
     */
    void setPositionOffsetY(float y);

    /**
     * Fast finish the animation.
     */
    void fastFinish();
}
