

package com.android.launcher3.taskbar

import android.content.Context
import com.android.launcher3.R
import com.android.launcher3.util.ResourceBasedOverride
import com.android.launcher3.util.ResourceBasedOverride.Overrides

/** Creates [TaskbarViewCallbacks] instances. */
open class TaskbarViewCallbacksFactory : ResourceBasedOverride {

    open fun create(
        activity: TaskbarActivityContext,
        controllers: TaskbarControllers,
        taskbarView: TaskbarView,
    ): TaskbarViewCallbacks = TaskbarViewCallbacks(activity, controllers, taskbarView)

    companion object {
        @JvmStatic
        fun newInstance(context: Context): TaskbarViewCallbacksFactory {
            return Overrides.getObject(
                TaskbarViewCallbacksFactory::class.java,
                context,
                R.string.taskbar_view_callbacks_factory_class,
            )
        }
    }
}
