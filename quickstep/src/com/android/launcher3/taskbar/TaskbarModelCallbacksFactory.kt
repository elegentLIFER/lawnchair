

package com.android.launcher3.taskbar

import android.content.Context
import com.android.launcher3.R
import com.android.launcher3.util.ResourceBasedOverride
import com.android.launcher3.util.ResourceBasedOverride.Overrides

/** Creates [TaskbarModelCallbacks] instances. */
open class TaskbarModelCallbacksFactory : ResourceBasedOverride {

    open fun create(
        activityContext: TaskbarActivityContext,
        container: TaskbarView,
    ): TaskbarModelCallbacks = TaskbarModelCallbacks(activityContext, container)

    companion object {
        @JvmStatic
        fun newInstance(context: Context): TaskbarModelCallbacksFactory {
            return Overrides.getObject(
                TaskbarModelCallbacksFactory::class.java,
                context,
                R.string.taskbar_model_callbacks_factory_class,
            )
        }
    }
}
