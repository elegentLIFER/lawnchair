

package com.android.wm.shell.flicker.utils

import android.app.Instrumentation
import android.content.Context
import android.provider.Settings
import android.util.Log
import com.android.compatibility.common.util.SystemUtil
import java.io.IOException

object MultiWindowUtils {
    private fun executeShellCommand(instrumentation: Instrumentation, cmd: String) {
        try {
            SystemUtil.runShellCommand(instrumentation, cmd)
        } catch (e: IOException) {
            Log.e(MultiWindowUtils::class.simpleName, "executeShellCommand error! $e")
        }
    }

    fun getDevEnableNonResizableMultiWindow(context: Context): Int =
        Settings.Global.getInt(
            context.contentResolver,
            Settings.Global.DEVELOPMENT_ENABLE_NON_RESIZABLE_MULTI_WINDOW
        )

    fun setDevEnableNonResizableMultiWindow(context: Context, configValue: Int) =
        Settings.Global.putInt(
            context.contentResolver,
            Settings.Global.DEVELOPMENT_ENABLE_NON_RESIZABLE_MULTI_WINDOW,
            configValue
        )

    fun setSupportsNonResizableMultiWindow(instrumentation: Instrumentation, configValue: Int) =
        executeShellCommand(
            instrumentation,
            createConfigSupportsNonResizableMultiWindowCommand(configValue)
        )

    fun resetMultiWindowConfig(instrumentation: Instrumentation) =
        executeShellCommand(instrumentation, resetMultiWindowConfigCommand)

    private fun createConfigSupportsNonResizableMultiWindowCommand(configValue: Int): String =
        "wm set-multi-window-config --supportsNonResizable $configValue"

    private const val resetMultiWindowConfigCommand: String = "wm reset-multi-window-config"
}
