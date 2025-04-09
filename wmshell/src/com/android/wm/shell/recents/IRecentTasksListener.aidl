

package com.android.wm.shell.recents;

import android.app.ActivityManager.RunningTaskInfo;

/**
 * Listener interface that Launcher attaches to SystemUI to get split-screen callbacks.
 */
oneway interface IRecentTasksListener {

    /**
     * Called when the set of recent tasks change.
     */
    void onRecentTasksChanged();

    /**
     * Called when a running task appears.
     */
    void onRunningTaskAppeared(in RunningTaskInfo taskInfo);

    /**
     * Called when a running task vanishes.
     */
    void onRunningTaskVanished(in RunningTaskInfo taskInfo);

    /**
     * Called when a running task changes.
     */
    void onRunningTaskChanged(in RunningTaskInfo taskInfo);

    /** A task has moved to front. */
    oneway void onTaskMovedToFront(in RunningTaskInfo taskInfo);
}
