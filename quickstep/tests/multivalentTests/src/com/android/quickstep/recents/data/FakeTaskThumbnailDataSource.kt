

package com.android.quickstep.recents.data

import android.graphics.Bitmap
import com.android.launcher3.util.CancellableTask
import com.android.quickstep.task.thumbnail.data.TaskThumbnailDataSource
import com.android.systemui.shared.recents.model.Task
import com.android.systemui.shared.recents.model.ThumbnailData
import java.util.function.Consumer
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class FakeTaskThumbnailDataSource : TaskThumbnailDataSource {

    val taskIdToBitmap: Map<Int, Bitmap> = (0..10).associateWith { mock() }
    val taskIdToUpdatingTask: MutableMap<Int, () -> Unit> = mutableMapOf()
    var shouldLoadSynchronously: Boolean = true

    /** Retrieves and sets a thumbnail on [task] from [taskIdToBitmap]. */
    override fun updateThumbnailInBackground(
        task: Task,
        callback: Consumer<ThumbnailData>
    ): CancellableTask<ThumbnailData>? {
        val thumbnailData = mock<ThumbnailData>()
        whenever(thumbnailData.thumbnail).thenReturn(taskIdToBitmap[task.key.id])
        val wrappedCallback = {
            task.thumbnail = thumbnailData
            callback.accept(thumbnailData)
        }
        if (shouldLoadSynchronously) {
            wrappedCallback()
        } else {
            taskIdToUpdatingTask[task.key.id] = wrappedCallback
        }
        return null
    }
}
