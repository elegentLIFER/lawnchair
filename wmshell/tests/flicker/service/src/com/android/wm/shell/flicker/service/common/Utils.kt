

package com.android.wm.shell.flicker.service.common

import android.app.Instrumentation
import android.platform.test.rule.NavigationModeRule
import android.platform.test.rule.PressHomeRule
import android.platform.test.rule.UnlockScreenRule
import android.tools.NavBar
import android.tools.Rotation
import android.tools.device.apphelpers.MessagingAppHelper
import android.tools.flicker.rules.ArtifactSaverRule
import android.tools.flicker.rules.ChangeDisplayOrientationRule
import android.tools.flicker.rules.LaunchAppRule
import android.tools.flicker.rules.RemoveAllTasksButHomeRule
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.rules.RuleChain

object Utils {
    private val instrumentation: Instrumentation = InstrumentationRegistry.getInstrumentation()

    fun testSetupRule(navigationMode: NavBar, rotation: Rotation): RuleChain {
        return RuleChain.outerRule(ArtifactSaverRule())
            .around(UnlockScreenRule())
            .around(NavigationModeRule(navigationMode.value, false))
            .around(
                LaunchAppRule(MessagingAppHelper(instrumentation), clearCacheAfterParsing = false)
            )
            .around(RemoveAllTasksButHomeRule())
            .around(
                ChangeDisplayOrientationRule(
                    rotation,
                    resetOrientationAfterTest = false,
                    clearCacheAfterParsing = false
                )
            )
            .around(PressHomeRule())
    }
}
