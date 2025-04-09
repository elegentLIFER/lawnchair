

package com.android.wm.shell.onehanded;

import android.graphics.Rect;

/**
 * The start or stop one handed transition callback for gesture to get latest timing to handle
 * touch region.(e.g: one handed activated, user tap out regions of displayArea to stop one handed)
 */
public interface OneHandedTransitionCallback {
    /**
     * Called when one handed mode entering or exiting transition starting
     */
    default void onStartTransition(boolean isEntering) {
    }

    /**
     * Called when start one handed transition finished
     */
    default void onStartFinished(Rect bounds) {
    }

    /**
     * Called when stop one handed transition finished
     */
    default void onStopFinished(Rect bounds) {
    }
}
