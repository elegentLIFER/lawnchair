

package com.android.wm.shell.desktopmode

import android.app.ActivityManager.RunningTaskInfo
import android.app.WindowConfiguration.ACTIVITY_TYPE_HOME
import android.app.WindowConfiguration.ACTIVITY_TYPE_STANDARD
import android.app.WindowConfiguration.WINDOWING_MODE_FREEFORM
import android.app.WindowConfiguration.WINDOWING_MODE_FULLSCREEN
import android.app.WindowConfiguration.WINDOWING_MODE_MULTI_WINDOW
import android.graphics.Rect
import android.view.Display.DEFAULT_DISPLAY
import com.android.wm.shell.MockToken
import com.android.wm.shell.TestRunningTaskInfoBuilder

class DesktopTestHelpers {
    companion object {
        /** Create a task that has windowing mode set to [WINDOWING_MODE_FREEFORM] */
        @JvmStatic
        @JvmOverloads
        fun createFreeformTask(
                displayId: Int = DEFAULT_DISPLAY,
                bounds: Rect? = null
        ): RunningTaskInfo {
            return TestRunningTaskInfoBuilder()
                    .setDisplayId(displayId)
                    .setToken(MockToken().token())
                    .setActivityType(ACTIVITY_TYPE_STANDARD)
                    .setWindowingMode(WINDOWING_MODE_FREEFORM)
                    .setLastActiveTime(100)
                    .apply { bounds?.let { setBounds(it) }}
                    .build()
        }

        /** Create a task that has windowing mode set to [WINDOWING_MODE_FULLSCREEN] */
        @JvmStatic
        @JvmOverloads
        fun createFullscreenTask(displayId: Int = DEFAULT_DISPLAY): RunningTaskInfo {
            return TestRunningTaskInfoBuilder()
                .setDisplayId(displayId)
                .setToken(MockToken().token())
                .setActivityType(ACTIVITY_TYPE_STANDARD)
                .setWindowingMode(WINDOWING_MODE_FULLSCREEN)
                .setLastActiveTime(100)
                .build()
        }

        /** Create a task that has windowing mode set to [WINDOWING_MODE_MULTI_WINDOW] */
        @JvmStatic
        @JvmOverloads
        fun createSplitScreenTask(displayId: Int = DEFAULT_DISPLAY): RunningTaskInfo {
            return TestRunningTaskInfoBuilder()
                .setDisplayId(displayId)
                .setToken(MockToken().token())
                .setActivityType(ACTIVITY_TYPE_STANDARD)
                .setWindowingMode(WINDOWING_MODE_MULTI_WINDOW)
                .setLastActiveTime(100)
                .build()
        }

        /** Create a new home task */
        @JvmStatic
        @JvmOverloads
        fun createHomeTask(displayId: Int = DEFAULT_DISPLAY): RunningTaskInfo {
            return TestRunningTaskInfoBuilder()
                    .setDisplayId(displayId)
                    .setToken(MockToken().token())
                    .setActivityType(ACTIVITY_TYPE_HOME)
                    .setWindowingMode(WINDOWING_MODE_FULLSCREEN)
                    .setLastActiveTime(100)
                    .build()
        }
    }
}
