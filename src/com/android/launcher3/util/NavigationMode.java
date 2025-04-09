

package com.android.launcher3.util;

import static com.android.launcher3.logging.StatsLogManager.LauncherEvent.LAUNCHER_NAVIGATION_MODE_2_BUTTON;
import static com.android.launcher3.logging.StatsLogManager.LauncherEvent.LAUNCHER_NAVIGATION_MODE_3_BUTTON;
import static com.android.launcher3.logging.StatsLogManager.LauncherEvent.LAUNCHER_NAVIGATION_MODE_GESTURE_BUTTON;

import com.android.launcher3.logging.StatsLogManager;

/**
 * Navigation mode used in the device.
 */
public enum NavigationMode {
    THREE_BUTTONS(false, 0, LAUNCHER_NAVIGATION_MODE_3_BUTTON),
    TWO_BUTTONS(true, 1, LAUNCHER_NAVIGATION_MODE_2_BUTTON),
    NO_BUTTON(true, 2, LAUNCHER_NAVIGATION_MODE_GESTURE_BUTTON);

    public final boolean hasGestures;
    public final int resValue;
    public final StatsLogManager.LauncherEvent launcherEvent;

    NavigationMode(boolean hasGestures, int resValue, StatsLogManager.LauncherEvent launcherEvent) {
        this.hasGestures = hasGestures;
        this.resValue = resValue;
        this.launcherEvent = launcherEvent;
    }
}
