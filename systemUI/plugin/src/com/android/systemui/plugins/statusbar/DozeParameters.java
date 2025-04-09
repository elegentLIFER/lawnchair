

package com.android.systemui.plugins.statusbar;

import com.android.systemui.plugins.annotations.ProvidesInterface;

/**
 * Retrieve doze information
 */
@ProvidesInterface(version = DozeParameters.VERSION)
public interface DozeParameters {
    int VERSION = 1;

    /**
     * Whether to doze when the screen turns off
     */
    boolean shouldControlScreenOff();
}
