

package com.android.launcher3.taskbar;

import static com.android.launcher3.logging.StatsLogManager.LauncherEvent.LAUNCHER_TASKBAR_ALLAPPS_BUTTON_LONG_PRESS;
import static com.android.launcher3.logging.StatsLogManager.LauncherEvent.LAUNCHER_TASKBAR_ALLAPPS_BUTTON_TAP;

import android.view.InputDevice;
import android.view.MotionEvent;
import android.view.View;

import com.android.internal.jank.Cuj;
import com.android.systemui.shared.system.InteractionJankMonitorWrapper;

/**
 * Callbacks for {@link TaskbarView} to interact with its controller.
 */
public class TaskbarViewCallbacks {

    private final TaskbarActivityContext mActivity;
    private final TaskbarControllers mControllers;
    private final TaskbarView mTaskbarView;

    public TaskbarViewCallbacks(TaskbarActivityContext activity, TaskbarControllers controllers,
            TaskbarView taskbarView) {
        mActivity = activity;
        mControllers = controllers;
        mTaskbarView = taskbarView;
    }

    public View.OnClickListener getIconOnClickListener() {
        return mActivity.getItemOnClickListener();
    }

    /** Trigger All Apps button click action. */
    protected void triggerAllAppsButtonClick(View v) {
        InteractionJankMonitorWrapper.begin(v, Cuj.CUJ_LAUNCHER_OPEN_ALL_APPS,
                /* tag= */ "TASKBAR_BUTTON");
        mActivity.getStatsLogManager().logger().log(LAUNCHER_TASKBAR_ALLAPPS_BUTTON_TAP);
        mControllers.taskbarAllAppsController.toggle();
    }

    /** Trigger All Apps button long click action. */
    protected void triggerAllAppsButtonLongClick() {
        mActivity.getStatsLogManager().logger().log(LAUNCHER_TASKBAR_ALLAPPS_BUTTON_LONG_PRESS);
    }

    public boolean isAllAppsButtonHapticFeedbackEnabled() {
        return false;
    }

    public View.OnLongClickListener getTaskbarDividerLongClickListener() {
        return v -> {
            mControllers.taskbarPinningController.showPinningView(v);
            return true;
        };
    }

    public View.OnTouchListener getTaskbarDividerRightClickListener() {
        return (v, event) -> {
            if (event.isFromSource(InputDevice.SOURCE_MOUSE)
                    && event.getButtonState() == MotionEvent.BUTTON_SECONDARY) {
                mControllers.taskbarPinningController.showPinningView(v);
                return true;
            }
            return false;
        };
    }

    public View.OnLongClickListener getIconOnLongClickListener() {
        return mControllers.taskbarDragController::startDragOnLongClick;
    }

    /** Gets the hover listener for the provided icon view. */
    public View.OnHoverListener getIconOnHoverListener(View icon) {
        return new TaskbarHoverToolTipController(mActivity, mTaskbarView, icon);
    }

    /**
     * Notifies launcher to update icon alignment.
     */
    public void notifyIconLayoutBoundsChanged() {
        mControllers.uiController.onIconLayoutBoundsChanged();
    }

    /**
     * Notifies the taskbar scrim when the visibility of taskbar changes.
     */
    public void notifyVisibilityChanged() {
        mControllers.taskbarScrimViewController.onTaskbarVisibilityChanged(
                mTaskbarView.getVisibility());
    }
}
