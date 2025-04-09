

package com.android.quickstep.task.thumbnail

import android.graphics.Bitmap
import android.graphics.Rect
import androidx.annotation.ColorInt

sealed class TaskThumbnailUiState {
    data object Uninitialized : TaskThumbnailUiState()
    data object LiveTile : TaskThumbnailUiState()
    data class BackgroundOnly(@ColorInt val backgroundColor: Int) : TaskThumbnailUiState()
    data class Snapshot(
        val bitmap: Bitmap,
        val drawnRect: Rect,
        @ColorInt val backgroundColor: Int
    ) : TaskThumbnailUiState()
}

data class TaskThumbnail(val taskId: Int, val isRunning: Boolean)
