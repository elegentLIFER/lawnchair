

package com.android.wm.shell.startingsurface;

import android.window.StartingWindowInfo;
import android.window.TaskSnapshot;

import com.android.wm.shell.common.ShellExecutor;

class SnapshotWindowCreator {
    private final ShellExecutor mMainExecutor;
    private final StartingSurfaceDrawer.StartingWindowRecordManager
            mStartingWindowRecordManager;

    SnapshotWindowCreator(ShellExecutor mainExecutor,
            StartingSurfaceDrawer.StartingWindowRecordManager startingWindowRecordManager) {
        mMainExecutor = mainExecutor;
        mStartingWindowRecordManager = startingWindowRecordManager;
    }

    void makeTaskSnapshotWindow(StartingWindowInfo startingWindowInfo, TaskSnapshot snapshot) {
        final int taskId = startingWindowInfo.taskInfo.taskId;
        // Remove any existing starting window for this task before adding.
        mStartingWindowRecordManager.removeWindow(taskId);
        final TaskSnapshotWindow surface = TaskSnapshotWindow.create(startingWindowInfo,
                startingWindowInfo.appToken, snapshot, mMainExecutor,
                () -> mStartingWindowRecordManager.removeWindow(taskId));
        if (surface != null) {
            final SnapshotWindowRecord tView = new SnapshotWindowRecord(surface,
                    startingWindowInfo.taskInfo.topActivityType, mMainExecutor,
                    taskId, mStartingWindowRecordManager);
            mStartingWindowRecordManager.addRecord(taskId, tView);
        }
    }

    private static class SnapshotWindowRecord extends StartingSurfaceDrawer.SnapshotRecord {
        private final TaskSnapshotWindow mTaskSnapshotWindow;

        SnapshotWindowRecord(TaskSnapshotWindow taskSnapshotWindow,
                int activityType, ShellExecutor removeExecutor, int id,
                StartingSurfaceDrawer.StartingWindowRecordManager recordManager) {
            super(activityType, removeExecutor, id, recordManager);
            mTaskSnapshotWindow = taskSnapshotWindow;
            mBGColor = mTaskSnapshotWindow.getBackgroundColor();
        }

        @Override
        protected void removeImmediately() {
            super.removeImmediately();
            mTaskSnapshotWindow.removeImmediately();
        }

        @Override
        protected boolean hasImeSurface() {
            return mTaskSnapshotWindow.hasImeSurface();
        }
    }
}
