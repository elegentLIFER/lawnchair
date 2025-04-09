
package com.android.launcher3.desktop

import android.app.IApplicationThread
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import android.view.SurfaceControl
import android.window.IRemoteTransitionFinishedCallback
import android.window.RemoteTransition
import android.window.RemoteTransitionStub
import android.window.TransitionInfo
import com.android.launcher3.statehandlers.DepthController
import com.android.launcher3.statemanager.StateManager
import com.android.launcher3.util.Executors.MAIN_EXECUTOR
import com.android.quickstep.SystemUiProxy
import com.android.quickstep.TaskViewUtils
import com.android.quickstep.views.DesktopTaskView
import com.android.wm.shell.common.desktopmode.DesktopModeTransitionSource
import java.util.function.Consumer

/** Manage recents related operations with desktop tasks */
class DesktopRecentsTransitionController(
    private val stateManager: StateManager<*, *>,
    private val systemUiProxy: SystemUiProxy,
    private val appThread: IApplicationThread,
    private val depthController: DepthController?
) {

    /** Launch desktop tasks from recents view */
    fun launchDesktopFromRecents(
        desktopTaskView: DesktopTaskView,
        callback: Consumer<Boolean>? = null
    ) {
        val animRunner =
            RemoteDesktopLaunchTransitionRunner(
                desktopTaskView,
                stateManager,
                depthController,
                callback
            )
        val transition = RemoteTransition(animRunner, appThread, "RecentsToDesktop")
        systemUiProxy.showDesktopApps(desktopTaskView.display.displayId, transition)
    }

    /** Launch desktop tasks from recents view */
    fun moveToDesktop(taskId: Int, transitionSource: DesktopModeTransitionSource) {
        systemUiProxy.moveToDesktop(taskId, transitionSource)
    }

    private class RemoteDesktopLaunchTransitionRunner(
        private val desktopTaskView: DesktopTaskView,
        private val stateManager: StateManager<*, *>,
        private val depthController: DepthController?,
        private val successCallback: Consumer<Boolean>?
    ) : RemoteTransitionStub() {

        override fun startAnimation(
            token: IBinder,
            info: TransitionInfo,
            t: SurfaceControl.Transaction,
            finishCallback: IRemoteTransitionFinishedCallback
        ) {
            val errorHandlingFinishCallback = Runnable {
                try {
                    finishCallback.onTransitionFinished(null /* wct */, null /* sct */)
                } catch (e: RemoteException) {
                    Log.e(TAG, "Failed to call finish callback for desktop recents animation", e)
                }
            }

            MAIN_EXECUTOR.execute {
                TaskViewUtils.composeRecentsDesktopLaunchAnimator(
                    desktopTaskView,
                    stateManager,
                    depthController,
                    info,
                    t
                ) {
                    errorHandlingFinishCallback.run()
                    successCallback?.accept(true)
                }
            }
        }
    }

    companion object {
        const val TAG = "DesktopRecentsTransitionController"
    }
}
