

package com.android.wm.shell.taskview;

import android.app.ActivityManager;
import android.graphics.Rect;
import android.view.SurfaceControl;

/**
 * A stub for SurfaceView used by {@link TaskViewTaskController}
 */
public interface TaskViewBase {
    /**
     * Returns the current bounds on screen for the task view.
     * @return
     */
    // TODO(b/266242294): Remove getBoundsOnScreen() and instead send the bounds from the TaskView
    //  to TaskViewTaskController.
    Rect getCurrentBoundsOnScreen();

    /**
     * This method should set the resize background color on the SurfaceView that is exposed to
     * clients.
     * See {@link android.view.SurfaceView#setResizeBackgroundColor(SurfaceControl.Transaction,
     * int)}
     */
    void setResizeBgColor(SurfaceControl.Transaction transaction, int color);

    /**
     * Called when a task appears on the TaskView. See
     * {@link TaskViewTaskController#onTaskAppeared(ActivityManager.RunningTaskInfo,
     * SurfaceControl)} for details.
     */
    default void onTaskAppeared(ActivityManager.RunningTaskInfo taskInfo, SurfaceControl leash) {
    }

    /**
     * Called when a task is vanished from the TaskView. See
     * {@link TaskViewTaskController#onTaskVanished(ActivityManager.RunningTaskInfo)} for details.
     */
    default void onTaskVanished(ActivityManager.RunningTaskInfo taskInfo) {
    }

    /**
     * Called when the task in the TaskView is changed. See
     * {@link TaskViewTaskController#onTaskInfoChanged(ActivityManager.RunningTaskInfo)} for details.
     */
    default void onTaskInfoChanged(ActivityManager.RunningTaskInfo taskInfo) {
    }
}
