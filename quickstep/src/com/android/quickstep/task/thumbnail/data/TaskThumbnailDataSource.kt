

package com.android.quickstep.task.thumbnail.data

import com.android.launcher3.util.CancellableTask
import com.android.systemui.shared.recents.model.Task
import com.android.systemui.shared.recents.model.ThumbnailData
import java.util.function.Consumer

interface TaskThumbnailDataSource {
    fun updateThumbnailInBackground(
        task: Task,
        callback: Consumer<ThumbnailData>
    ): CancellableTask<ThumbnailData>?
}
