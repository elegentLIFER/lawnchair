
package com.android.launcher3.taskbar;

import static com.android.launcher3.Utilities.dpToPx;
import static com.android.launcher3.Utilities.dpiFromPx;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import androidx.core.content.res.ResourcesCompat;

import com.android.launcher3.DeviceProfile;
import com.android.launcher3.R;
import com.android.launcher3.config.FeatureFlags;

/**
 * Utility class that contains the different taskbar thresholds logic.
 */
public class TaskbarThresholdUtils {

    // We divide the screen into this many parts, and use the result to scale the thresholds to
    // any size device. Note that this value was calculated arbitrarily by using two tablet devices
    // as data points.
    private static final float SCREEN_UNITS = 1 / 80f;

    private static int getThreshold(Resources r, DeviceProfile dp, int thresholdDimen,
            int multiplierDimen) {
        if (!FeatureFlags.ENABLE_DYNAMIC_TASKBAR_THRESHOLDS.get()) {
            return r.getDimensionPixelSize(thresholdDimen);
        }

        float landscapeScreenHeight = dp.isLandscape ? dp.heightPx : dp.widthPx;
        float screenPart = (landscapeScreenHeight * SCREEN_UNITS);
        float defaultDp = dpiFromPx(screenPart, DisplayMetrics.DENSITY_DEVICE_STABLE);
        float thisDp = dpToPx(defaultDp);
        float multiplier = ResourcesCompat.getFloat(r, multiplierDimen);
        float value = (thisDp) * multiplier;

        return Math.round(value);
    }

    /**
     * Returns the threshold that determines if we should show taskbar.
     */
    public static int getFromNavThreshold(Resources r, DeviceProfile dp) {
        return getThreshold(r, dp, R.dimen.taskbar_from_nav_threshold,
                R.dimen.taskbar_nav_threshold_mult);
    }

    /**
     * Returns the threshold that we start moving the app window.
     */
    public static int getAppWindowThreshold(Resources r, DeviceProfile dp) {
        return getThreshold(r, dp, R.dimen.taskbar_app_window_threshold,
                R.dimen.taskbar_app_window_threshold_mult);
    }

    /**
     * Returns the threshold for whether we land in home or overview.
     */
    public static int getHomeOverviewThreshold(Resources r, DeviceProfile dp) {
        return getThreshold(r, dp, R.dimen.taskbar_home_overview_threshold,
                R.dimen.taskbar_home_overview_threshold_mult);
    }

    /**
     * Returns the threshold that we use to allow swipe to catch up to finger.
     */
    public static int getCatchUpThreshold(Resources r, DeviceProfile dp) {
        return getThreshold(r, dp, R.dimen.taskbar_catch_up_threshold,
                R.dimen.taskbar_catch_up_threshold_mult);
    }
}
