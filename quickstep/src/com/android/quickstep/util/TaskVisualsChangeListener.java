

package com.android.quickstep.util;

import android.os.UserHandle;

import com.android.systemui.shared.recents.model.Task;
import com.android.systemui.shared.recents.model.ThumbnailData;

/**
 * Listener for receiving various task properties changes
 */
public interface TaskVisualsChangeListener {

    /**
     * Called when the task thumbnail changes
     */
    default Task onTaskThumbnailChanged(int taskId, ThumbnailData thumbnailData) {
        return null;
    }

    /**
     * Called when the icon for a task changes
     */
    default void onTaskIconChanged(String pkg, UserHandle user) {}

    /**
     * Called when the icon for a task changes
     */
    default void onTaskIconChanged(int taskId) {}
}
