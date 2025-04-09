

package com.android.quickstep.recents.data

import com.android.quickstep.util.GroupTask
import java.util.function.Consumer

interface RecentTasksDataSource {
    fun getTasks(callback: Consumer<List<GroupTask>>?): Int
}
