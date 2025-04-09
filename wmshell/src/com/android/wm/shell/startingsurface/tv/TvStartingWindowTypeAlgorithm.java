

package com.android.wm.shell.startingsurface.tv;

import static android.window.StartingWindowInfo.STARTING_WINDOW_TYPE_NONE;

import android.window.StartingWindowInfo;

import com.android.wm.shell.startingsurface.StartingWindowTypeAlgorithm;

/**
 * Algorithm for determining the type of a new starting window on Android TV.
 * For now we do not want to show any splash screens on Android TV.
 */
public class TvStartingWindowTypeAlgorithm implements StartingWindowTypeAlgorithm {
    @Override
    public int getSuggestedWindowType(StartingWindowInfo windowInfo) {
        // For now we do not want to show any splash screens on TV.
        return STARTING_WINDOW_TYPE_NONE;
    }
}
