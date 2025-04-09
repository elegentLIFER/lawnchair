
package com.android.systemui.unfold.system

import android.app.ActivityManager
import android.app.ActivityManager.RunningTaskInfo
import android.app.WindowConfiguration
import android.os.Trace
import com.android.systemui.shared.system.TaskStackChangeListener
import com.android.systemui.shared.system.TaskStackChangeListeners
import com.android.systemui.unfold.util.CurrentActivityTypeProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActivityManagerActivityTypeProvider
@Inject
constructor(private val activityManager: ActivityManager) : CurrentActivityTypeProvider {

    override val isHomeActivity: Boolean?
        get() = _isHomeActivity

    @Volatile private var _isHomeActivity: Boolean? = null

    override fun init() {
        _isHomeActivity = activityManager.isOnHomeActivity()
        TaskStackChangeListeners.getInstance().registerTaskStackListener(taskStackChangeListener)
    }

    override fun uninit() {
        TaskStackChangeListeners.getInstance().unregisterTaskStackListener(taskStackChangeListener)
    }

    private val taskStackChangeListener =
        object : TaskStackChangeListener {
            override fun onTaskMovedToFront(taskInfo: RunningTaskInfo) {
                _isHomeActivity = taskInfo.isHomeActivity()
            }
        }

    private fun RunningTaskInfo.isHomeActivity(): Boolean =
        topActivityType == WindowConfiguration.ACTIVITY_TYPE_HOME

    private fun ActivityManager.isOnHomeActivity(): Boolean? {
        try {
            Trace.beginSection("isOnHomeActivity")
            return getRunningTasks(/* maxNum= */ 1)?.firstOrNull()?.isHomeActivity()
        } finally {
            Trace.endSection()
        }
    }
}
