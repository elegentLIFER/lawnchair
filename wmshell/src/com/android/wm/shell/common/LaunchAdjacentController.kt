
package com.android.wm.shell.common

import android.window.WindowContainerToken
import android.window.WindowContainerTransaction
import com.android.wm.shell.protolog.ShellProtoLogGroup.WM_SHELL_TASK_ORG
import com.android.wm.shell.util.KtProtoLog

/**
 * Controller to manage behavior of activities launched with
 * [android.content.Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT].
 */
class LaunchAdjacentController(private val syncQueue: SyncTransactionQueue) {

    /** Allows to temporarily disable launch adjacent handling */
    var launchAdjacentEnabled: Boolean = true
        set(value) {
            if (field != value) {
                KtProtoLog.d(WM_SHELL_TASK_ORG, "set launch adjacent flag root enabled=%b", value)
                field = value
                container?.let { c ->
                    if (value) {
                        enableContainer(c)
                    } else {
                        disableContainer((c))
                    }
                }
            }
        }
    private var container: WindowContainerToken? = null

    /**
     * Set [container] as the new launch adjacent flag root container.
     *
     * If launch adjacent handling is disabled through [setLaunchAdjacentEnabled], won't set the
     * container until after it is enabled again.
     *
     * @see WindowContainerTransaction.setLaunchAdjacentFlagRoot
     */
    fun setLaunchAdjacentRoot(container: WindowContainerToken) {
        KtProtoLog.d(WM_SHELL_TASK_ORG, "set new launch adjacent flag root container")
        this.container = container
        if (launchAdjacentEnabled) {
            enableContainer(container)
        }
    }

    /**
     * Clear a container previously set through [setLaunchAdjacentRoot].
     *
     * Always clears the container, regardless of [launchAdjacentEnabled] value.
     *
     * @see WindowContainerTransaction.clearLaunchAdjacentFlagRoot
     */
    fun clearLaunchAdjacentRoot() {
        KtProtoLog.d(WM_SHELL_TASK_ORG, "clear launch adjacent flag root container")
        container?.let {
            disableContainer(it)
            container = null
        }
    }

    private fun enableContainer(container: WindowContainerToken) {
        KtProtoLog.v(WM_SHELL_TASK_ORG, "enable launch adjacent flag root container")
        val wct = WindowContainerTransaction()
        wct.setLaunchAdjacentFlagRoot(container)
        syncQueue.queue(wct)
    }

    private fun disableContainer(container: WindowContainerToken) {
        KtProtoLog.v(WM_SHELL_TASK_ORG, "disable launch adjacent flag root container")
        val wct = WindowContainerTransaction()
        wct.clearLaunchAdjacentFlagRoot(container)
        syncQueue.queue(wct)
    }
}
