

package com.android.quickstep.recents.data

import com.android.quickstep.util.GroupTask
import java.util.function.Consumer

class FakeRecentTasksDataSource : RecentTasksDataSource {
    var taskList: List<GroupTask> = listOf()

    override fun getTasks(callback: Consumer<List<GroupTask>>?): Int {
        callback?.accept(taskList)
        return 0
    }

    fun seedTasks(tasks: List<GroupTask>) {
        taskList = tasks
    }
}
