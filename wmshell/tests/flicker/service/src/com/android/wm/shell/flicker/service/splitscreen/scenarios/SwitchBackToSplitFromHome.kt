

package com.android.wm.shell.flicker.service.splitscreen.scenarios

import android.app.Instrumentation
import android.tools.NavBar
import android.tools.Rotation
import android.tools.traces.parsers.WindowManagerStateHelper
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.android.launcher3.tapl.LauncherInstrumentation
import com.android.wm.shell.flicker.service.common.Utils
import com.android.wm.shell.flicker.utils.SplitScreenUtils
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

@Ignore("Base Test Class")
abstract class SwitchBackToSplitFromHome
@JvmOverloads
constructor(val rotation: Rotation = Rotation.ROTATION_0) {
    private val instrumentation: Instrumentation = InstrumentationRegistry.getInstrumentation()
    private val tapl = LauncherInstrumentation()
    private val wmHelper = WindowManagerStateHelper(instrumentation)
    private val device = UiDevice.getInstance(instrumentation)
    private val primaryApp = SplitScreenUtils.getPrimary(instrumentation)
    private val secondaryApp = SplitScreenUtils.getSecondary(instrumentation)

    @Rule @JvmField val testSetupRule = Utils.testSetupRule(NavBar.MODE_GESTURAL, rotation)

    @Before
    fun setup() {
        tapl.setEnableRotation(true)
        tapl.setExpectedRotation(rotation.value)

        SplitScreenUtils.enterSplit(wmHelper, tapl, device, primaryApp, secondaryApp, rotation)

        tapl.goHome()
        wmHelper.StateSyncBuilder().withHomeActivityVisible().waitForAndVerify()
    }

    @Test
    open fun switchBackToSplitFromHome() {
        tapl.workspace.quickSwitchToPreviousApp()
        SplitScreenUtils.waitForSplitComplete(wmHelper, primaryApp, secondaryApp)
    }

    @After
    fun teardown() {
        primaryApp.exit(wmHelper)
        secondaryApp.exit(wmHelper)
    }
}
