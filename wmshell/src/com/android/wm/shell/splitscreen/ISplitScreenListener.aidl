

package com.android.wm.shell.splitscreen;

/**
 * Listener interface that Launcher attaches to SystemUI to get split-screen callbacks.
 */
oneway interface ISplitScreenListener {

    /**
     * Called when the stage position changes.
     */
    void onStagePositionChanged(int stage, int position);

    /**
     * Called when a task changes stages.
     */
    void onTaskStageChanged(int taskId, int stage, boolean visible);
}
