

package com.android.launcher3.taskbar.customization

import com.android.launcher3.config.FeatureFlags.enableTaskbarPinning
import com.android.launcher3.taskbar.TaskbarActivityContext
import com.android.launcher3.taskbar.TaskbarControllers
import com.android.launcher3.taskbar.TaskbarRecentAppsController
import com.android.launcher3.util.DisplayController

/** Evaluates all the features taskbar can have. */
class TaskbarFeatureEvaluator(
    private val taskbarActivityContext: TaskbarActivityContext,
    private val taskbarControllers: TaskbarControllers,
) {

    val hasAllApps = true
    val hasAppIcons = true
    val hasBubbles = false
    val hasNavButtons = taskbarActivityContext.isThreeButtonNav

    val hasRecents: Boolean
        get() = taskbarControllers.taskbarRecentAppsController.isEnabled

    val hasDivider: Boolean
        get() = enableTaskbarPinning() || hasRecents

    val isTransient: Boolean
        get() = DisplayController.isTransientTaskbar(taskbarActivityContext)
}
