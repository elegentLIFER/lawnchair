

package com.android.wm.shell.sysui;

import android.graphics.Rect;

/**
 * Callbacks for when the Display IME changes.
 */
public interface DisplayImeChangeListener {
    /**
     * Called when the ime bounds change.
     */
    default void onImeBoundsChanged(int displayId, Rect bounds) {}

    /**
     * Called when the IME visibility change.
     */
    default void onImeVisibilityChanged(int displayId, boolean isShowing) {}
}
