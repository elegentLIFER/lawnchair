

package com.android.systemui.shared.system

import android.app.ActivityManager

/** Kotlin extensions for [ActivityManager] */
object ActivityManagerKt {

    /**
     * Returns `true` whether the app with the given package name has an activity at the top of the
     * most recent task; `false` otherwise
     */
    fun ActivityManager.isInForeground(packageName: String): Boolean {
        val tasks: List<ActivityManager.RunningTaskInfo> = getRunningTasks(1)
        return tasks.isNotEmpty() && packageName == tasks[0].topActivity?.packageName
    }
}
