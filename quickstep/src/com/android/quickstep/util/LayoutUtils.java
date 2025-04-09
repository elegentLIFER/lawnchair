
package com.android.quickstep.util;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

import com.android.launcher3.DeviceProfile;
import com.android.launcher3.util.DisplayController;
import com.android.launcher3.util.NavigationMode;
import com.android.quickstep.LauncherActivityInterface;
import com.android.quickstep.orientation.RecentsPagedOrientationHandler;

public class LayoutUtils {

    /**
     * The height for the swipe up motion
     */
    public static float getDefaultSwipeHeight(Context context, DeviceProfile dp) {
        float swipeHeight = dp.allAppsCellHeightPx - dp.allAppsIconTextSizePx;
        if (DisplayController.getNavigationMode(context) == NavigationMode.NO_BUTTON) {
            swipeHeight -= dp.getInsets().bottom;
        }
        return swipeHeight;
    }

    public static int getShelfTrackingDistance(Context context, DeviceProfile dp,
            RecentsPagedOrientationHandler orientationHandler) {
        // Track the bottom of the window.
        Rect taskSize = new Rect();
        LauncherActivityInterface.INSTANCE.calculateTaskSize(context, dp, taskSize,
                orientationHandler);
        return orientationHandler.getDistanceToBottomOfRect(dp, taskSize);
    }

    /**
     * Recursively sets view and all children enabled/disabled.
     * @param view Top most parent view to change.
     * @param enabled True = enable, False = disable.
     */
    public static void setViewEnabled(View view, boolean enabled) {
        view.setEnabled(enabled);
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                setViewEnabled(((ViewGroup) view).getChildAt(i), enabled);
            }
        }
    }
}
