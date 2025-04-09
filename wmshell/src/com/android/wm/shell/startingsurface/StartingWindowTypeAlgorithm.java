

package com.android.wm.shell.startingsurface;

import android.window.StartingWindowInfo;

/**
 * Used by {@link StartingWindowController} for determining the type of a new starting window.
 */
public interface StartingWindowTypeAlgorithm {
    /**
     * @return suggested type for the given window.
     */
    @StartingWindowInfo.StartingWindowType
    int getSuggestedWindowType(StartingWindowInfo windowInfo);
}
