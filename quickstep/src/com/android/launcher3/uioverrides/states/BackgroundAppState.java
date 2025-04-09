
package com.android.launcher3.uioverrides.states;

import static com.android.launcher3.Flags.enableScalingRevealHomeAnimation;
import static com.android.launcher3.logging.StatsLogManager.LAUNCHER_STATE_BACKGROUND;
import static com.android.quickstep.TaskAnimationManager.ENABLE_SHELL_TRANSITIONS;

import android.content.Context;
import android.graphics.Color;

import com.android.launcher3.DeviceProfile;
import com.android.launcher3.Launcher;
import com.android.launcher3.allapps.AllAppsTransitionController;
import com.android.quickstep.util.BaseDepthController;
import com.android.quickstep.util.LayoutUtils;
import com.android.quickstep.views.RecentsView;

/**
 * State indicating that the Launcher is behind an app
 */
public class BackgroundAppState extends OverviewState {

    private static final int STATE_FLAGS = FLAG_DISABLE_RESTORE | FLAG_RECENTS_VIEW_VISIBLE
            | FLAG_WORKSPACE_INACCESSIBLE | FLAG_NON_INTERACTIVE | FLAG_CLOSE_POPUPS;

    public BackgroundAppState(int id) {
        this(id, LAUNCHER_STATE_BACKGROUND);
    }

    protected BackgroundAppState(int id, int logContainer) {
        super(id, logContainer, STATE_FLAGS);
    }

    @Override
    public float getVerticalProgress(Launcher launcher) {
        if (launcher.getDeviceProfile().isVerticalBarLayout()) {
            return super.getVerticalProgress(launcher);
        }
        RecentsView recentsView = launcher.getOverviewPanel();
        int transitionLength = LayoutUtils.getShelfTrackingDistance(launcher,
                launcher.getDeviceProfile(),
                recentsView.getPagedOrientationHandler());
        AllAppsTransitionController controller = launcher.getAllAppsController();
        float scrollRange = Math.max(controller.getShiftRange(), 1);
        float progressDelta = (transitionLength / scrollRange);
        return super.getVerticalProgress(launcher) + progressDelta;
    }

    @Override
    public float[] getOverviewScaleAndOffset(Launcher launcher) {
        return getOverviewScaleAndOffsetForBackgroundState(launcher.getOverviewPanel());
    }

    @Override
    public float getOverviewFullscreenProgress() {
        return 1;
    }

    @Override
    public int getVisibleElements(Launcher launcher) {
        return super.getVisibleElements(launcher)
                & ~OVERVIEW_ACTIONS
                & ~CLEAR_ALL_BUTTON
                & ~VERTICAL_SWIPE_INDICATOR;
    }

    @Override
    public boolean displayOverviewTasksAsGrid(DeviceProfile deviceProfile) {
        return false;
    }

    @Override
    public boolean showTaskThumbnailSplash() {
        return true;
    }

    @Override
    protected float getDepthUnchecked(Context context) {
        if (Launcher.getLauncher(context).areDesktopTasksVisible()) {
            // Don't blur the background while desktop tasks are visible
            return BaseDepthController.DEPTH_0_PERCENT;
        } else if (enableScalingRevealHomeAnimation()) {
            return BaseDepthController.DEPTH_70_PERCENT;
        } else {
            return 1f;
        }
    }

    @Override
    public int getWorkspaceScrimColor(Launcher launcher) {
        return Color.TRANSPARENT;
    }

    @Override
    public boolean isTaskbarAlignedWithHotseat(Launcher launcher) {
        if (ENABLE_SHELL_TRANSITIONS) return false;
        return super.isTaskbarAlignedWithHotseat(launcher);
    }

    @Override
    public boolean disallowTaskbarGlobalDrag() {
        // Enable global drag in overview
        return false;
    }

    @Override
    public boolean allowTaskbarInitialSplitSelection() {
        // Disallow split select from taskbar items in overview
        return false;
    }

    public static float[] getOverviewScaleAndOffsetForBackgroundState(
            RecentsView recentsView) {
        return new float[] {recentsView.getMaxScaleForFullScreen(), NO_OFFSET};
    }
}
