

package com.android.quickstep.recents.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow

// This is far from complete but serves the purpose of enabling refactoring in other areas
class RecentsViewData {
    val fullscreenProgress = MutableStateFlow(1f)

    // This is typically a View concern but it is used to invalidate rendering in other Views
    val scale = MutableStateFlow(1f)
}
