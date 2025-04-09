

package com.android.wm.shell.windowdecor;

/**
 * Holds the state of a drag resize.
 */
interface TaskDragResizer {

    /**
     * Returns true if task is currently being resized or animating the final transition after
     * a resize is complete.
     */
    boolean isResizingOrAnimating();
}
