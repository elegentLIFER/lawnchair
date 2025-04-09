
package com.android.launcher3.uioverrides.states;

import static com.android.launcher3.logging.StatsLogManager.LAUNCHER_STATE_BACKGROUND;

import android.graphics.Color;

import com.android.launcher3.DeviceProfile;
import com.android.launcher3.Launcher;
import com.android.launcher3.R;
import com.android.launcher3.util.Themes;

/**
 * State to indicate we are about to launch a recent task. Note that this state is only used when
 * quick switching from launcher; quick switching from an app uses LauncherSwipeHandler.
 * @see com.android.quickstep.GestureState.GestureEndTarget#NEW_TASK
 */
public class QuickSwitchState extends BackgroundAppState {

    public QuickSwitchState(int id) {
        super(id, LAUNCHER_STATE_BACKGROUND);
    }

    @Override
    public ScaleAndTranslation getWorkspaceScaleAndTranslation(Launcher launcher) {
        float shiftRange = launcher.getAllAppsController().getShiftRange();
        float shiftProgress = getVerticalProgress(launcher) - NORMAL.getVerticalProgress(launcher);
        float translationY = shiftProgress * shiftRange;
        return new ScaleAndTranslation(0.9f, 0, translationY);
    }

    @Override
    public int getWorkspaceScrimColor(Launcher launcher) {
        if (launcher.areDesktopTasksVisible()) {
            // No scrim while desktop tasks are visible
            return Color.TRANSPARENT;
        }
        DeviceProfile dp = launcher.getDeviceProfile();
        if (dp.isTaskbarPresentInApps) {
            return launcher.getColor(R.color.taskbar_background);
        }
        return Themes.getAttrColor(launcher, R.attr.overviewScrimColor);
    }

    @Override
    public float getVerticalProgress(Launcher launcher) {
        // Don't move all apps shelf while quick-switching (just let it fade).
        return 1f;
    }

    @Override
    public int getVisibleElements(Launcher launcher) {
        return NONE;
    }

    @Override
    public boolean isTaskbarStashed(Launcher launcher) {
        return !launcher.getDeviceProfile().isTaskbarPresentInApps;
    }

    @Override
    public boolean isTaskbarAlignedWithHotseat(Launcher launcher) {
        return false;
    }
}
