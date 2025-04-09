

@file:JvmName("AppCompatUtils")

package com.android.wm.shell.compatui

import android.app.TaskInfo
fun isSingleTopActivityTranslucent(task: TaskInfo) =
    task.isTopActivityTransparent && task.numActivities == 1

