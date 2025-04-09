

package com.android.quickstep.task.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow

class TaskViewData {
    // This is typically a View concern but it is used to invalidate rendering in other Views
    val scale = MutableStateFlow(1f)
}
