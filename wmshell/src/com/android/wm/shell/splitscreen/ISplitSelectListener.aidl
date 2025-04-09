

package com.android.wm.shell.splitscreen;

import android.app.ActivityManager.RunningTaskInfo;
import android.graphics.Rect;
/**
 * Listener interface that Launcher attaches to SystemUI to get split-select callbacks.
 */
interface ISplitSelectListener {
    /**
     * Called when a task requests to enter split select
     */
    boolean onRequestSplitSelect(in RunningTaskInfo taskInfo, int splitPosition, in Rect taskBounds);
}
