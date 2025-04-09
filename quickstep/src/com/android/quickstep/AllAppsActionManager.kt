

package com.android.quickstep

import android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_ACCESSIBILITY_ALL_APPS
import android.app.PendingIntent
import android.app.RemoteAction
import android.content.Context
import android.graphics.drawable.Icon
import android.view.accessibility.AccessibilityManager
import com.android.launcher3.R
import java.util.concurrent.Executor

/**
 * Registers a [RemoteAction] for toggling All Apps if needed.
 *
 * We need this action when either [isHomeAndOverviewSame] or [isTaskbarPresent] is `true`. When
 * home and overview are the same, we can control Launcher's or Taskbar's All Apps tray. If they are
 * not the same, but Taskbar is present, we can only control Taskbar's tray.
 */
class AllAppsActionManager(
    private val context: Context,
    private val bgExecutor: Executor,
    private val createAllAppsPendingIntent: () -> PendingIntent,
) {

    /** `true` if home and overview are the same Activity. */
    var isHomeAndOverviewSame = false
        set(value) {
            field = value
            updateSystemAction()
        }

    /** `true` if Taskbar is enabled. */
    var isTaskbarPresent = false
        set(value) {
            field = value
            updateSystemAction()
        }

    /** `true` if the action should be registered. */
    var isActionRegistered = false
        private set

    private fun updateSystemAction() {
        val shouldRegisterAction = isHomeAndOverviewSame || isTaskbarPresent
        if (isActionRegistered == shouldRegisterAction) return
        isActionRegistered = shouldRegisterAction

        bgExecutor.execute {
            val accessibilityManager =
                context.getSystemService(AccessibilityManager::class.java) ?: return@execute
            if (shouldRegisterAction) {
                accessibilityManager.registerSystemAction(
                    RemoteAction(
                        Icon.createWithResource(context, R.drawable.ic_apps),
                        context.getString(R.string.all_apps_label),
                        context.getString(R.string.all_apps_label),
                        createAllAppsPendingIntent(),
                    ),
                    GLOBAL_ACTION_ACCESSIBILITY_ALL_APPS,
                )
            } else {
                accessibilityManager.unregisterSystemAction(GLOBAL_ACTION_ACCESSIBILITY_ALL_APPS)
            }
        }
    }

    fun onDestroy() {
        isActionRegistered = false
        context
            .getSystemService(AccessibilityManager::class.java)
            ?.unregisterSystemAction(
                GLOBAL_ACTION_ACCESSIBILITY_ALL_APPS,
            )
    }
}
