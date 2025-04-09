

package com.android.wm.shell.flicker.service.desktopmode.scenarios

import android.app.Instrumentation
import android.tools.NavBar
import android.tools.Rotation
import android.tools.traces.parsers.WindowManagerStateHelper
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.android.launcher3.tapl.LauncherInstrumentation
import com.android.server.wm.flicker.helpers.DesktopModeAppHelper
import com.android.server.wm.flicker.helpers.SimpleAppHelper
import com.android.window.flags.Flags
import com.android.wm.shell.flicker.service.common.Utils
import org.junit.After
import org.junit.Assume
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test


@Ignore("Base Test Class")
abstract class ResizeAppWithCornerResize
@JvmOverloads
constructor(val rotation: Rotation = Rotation.ROTATION_0) {

    private val instrumentation: Instrumentation = InstrumentationRegistry.getInstrumentation()
    private val tapl = LauncherInstrumentation()
    private val wmHelper = WindowManagerStateHelper(instrumentation)
    private val device = UiDevice.getInstance(instrumentation)
    private val testApp = DesktopModeAppHelper(SimpleAppHelper(instrumentation))

    @Rule @JvmField val testSetupRule = Utils.testSetupRule(NavBar.MODE_GESTURAL, rotation)

    @Before
    fun setup() {
        Assume.assumeTrue(Flags.enableDesktopWindowingMode() && tapl.isTablet)
        tapl.setEnableRotation(true)
        tapl.setExpectedRotation(rotation.value)
        testApp.enterDesktopWithDrag(wmHelper, device)
    }

    @Test
    open fun resizeAppWithCornerResize() {
        testApp.cornerResize(wmHelper, device, DesktopModeAppHelper.Corners.RIGHT_TOP, 50, -50)
    }

    @After
    fun teardown() {
        testApp.exit(wmHelper)
    }
}
