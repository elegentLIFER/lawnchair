

package com.android.systemui.plugins;

import android.content.Context;

import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;

/**
 * Used to capture Falsing data (related to unlocking the screen).
 *
 * The intent is that the data can later be analyzed to validate the quality of the
 * {@link FalsingManager}.
 */
@ProvidesInterface(action = FalsingPlugin.ACTION, version = FalsingPlugin.VERSION)
@DependsOn(target = FalsingManager.class)
public interface FalsingPlugin extends Plugin {
    String ACTION = "com.android.systemui.action.FALSING_PLUGIN";
    int VERSION = 2;

    /**
     * Called when there is data to be recorded.
     *
     * @param success Indicates whether the action is considered a success.
     * @param data The raw data to be recorded for analysis.
     */
    default void dataCollected(boolean success, byte[] data) { }

    /**
     * Return a {@link FalsingManager} to be used in place of the system's default.
     *
     * @param context
     */
    default FalsingManager getFalsingManager(Context context) {
        return null;
    }
}
