

package com.android.quickstep.recents.data

import com.android.systemui.shared.recents.model.Task
import kotlinx.coroutines.flow.Flow

interface RecentTasksRepository {
    /** Gets all the recent tasks, refreshing from data sources if [forceRefresh] is true. */
    fun getAllTaskData(forceRefresh: Boolean = false): Flow<List<Task>>

    /**
     * Gets the data associated with a task that has id [taskId]. Flow will settle on null if the
     * task was not found.
     */
    fun getTaskDataById(taskId: Int): Flow<Task?>

    /**
     * Sets the tasks that are visible, indicating that properties relating to visuals need to be
     * populated e.g. icons/thumbnails etc.
     */
    fun setVisibleTasks(visibleTaskIdList: List<Int>)
}
