

package com.android.wm.shell.common;

import android.app.ActivityManager.RunningTaskInfo;
import android.app.ITaskStackListener;
import android.content.ComponentName;
import android.window.TaskSnapshot;

import androidx.annotation.BinderThread;

/**
 * An interface to track task stack changes. Classes should implement this instead of
 * {@link ITaskStackListener} to reduce IPC calls from system services.
 */
public interface TaskStackListenerCallback {

    default void onRecentTaskListUpdated() { }

    default void onRecentTaskListFrozenChanged(boolean frozen) { }

    @BinderThread
    default void onTaskStackChangedBackground() { }

    default void onTaskStackChanged() { }

    default void onTaskProfileLocked(RunningTaskInfo taskInfo, int userId) { }

    default void onTaskDisplayChanged(int taskId, int newDisplayId) { }

    default void onTaskCreated(int taskId, ComponentName componentName) { }

    default void onTaskRemoved(int taskId) { }

    default void onTaskMovedToFront(int taskId) { }

    default void onTaskMovedToFront(RunningTaskInfo taskInfo) {
        onTaskMovedToFront(taskInfo.taskId);
    }

    default void onTaskDescriptionChanged(RunningTaskInfo taskInfo) { }

    /**
     * @return whether the snapshot is consumed and the lifecycle of the snapshot extends beyond
     *         the lifecycle of this callback.
     */
    default boolean onTaskSnapshotChanged(int taskId, TaskSnapshot snapshot) {
        return false;
    }

    default void onBackPressedOnTaskRoot(RunningTaskInfo taskInfo) { }

    default void onActivityRestartAttempt(RunningTaskInfo task, boolean homeTaskVisible,
            boolean clearedTask, boolean wasVisible) { }

    default void onActivityPinned(String packageName, int userId, int taskId, int stackId) { }

    default void onActivityUnpinned() { }

    default void onActivityForcedResizable(String packageName, int taskId, int reason) { }

    default void onActivityDismissingDockedStack() { }

    default void onActivityLaunchOnSecondaryDisplayFailed() { }

    default void onActivityLaunchOnSecondaryDisplayFailed(RunningTaskInfo taskInfo) {
        onActivityLaunchOnSecondaryDisplayFailed();
    }

    default void onActivityLaunchOnSecondaryDisplayRerouted() { }

    default void onActivityLaunchOnSecondaryDisplayRerouted(RunningTaskInfo taskInfo) {
        onActivityLaunchOnSecondaryDisplayRerouted();
    }

    default void onActivityRequestedOrientationChanged(int taskId, int requestedOrientation) { }

    default void onActivityRotation(int displayId) { }
}
