

package com.android.quickstep.util;

import androidx.annotation.NonNull;

import com.android.quickstep.views.TaskView;
import com.android.systemui.shared.recents.model.Task;

import java.util.List;

/**
 * A {@link Task} container that can contain N number of tasks that are part of the desktop in
 * recent tasks list.
 */
public class DesktopTask extends GroupTask {

    @NonNull
    public final List<Task> tasks;

    public DesktopTask(@NonNull List<Task> tasks) {
        super(tasks.get(0), null, null, TaskView.Type.DESKTOP);
        this.tasks = tasks;
    }

    @Override
    public boolean containsTask(int taskId) {
        for (Task task : tasks) {
            if (task.key.id == taskId) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasMultipleTasks() {
        return true;
    }

    @Override
    @NonNull
    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public DesktopTask copy() {
        return new DesktopTask(tasks);
    }

    @Override
    public String toString() {
        return "type=" + taskViewType + " tasks=" + tasks;
    }

}
