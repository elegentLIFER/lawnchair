

package com.android.wm.shell.flicker.service.splitscreen.scenarios

import android.app.Instrumentation
import android.tools.NavBar
import android.tools.Rotation
import android.tools.flicker.rules.ChangeDisplayOrientationRule
import android.tools.traces.parsers.WindowManagerStateHelper
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.android.launcher3.tapl.LauncherInstrumentation
import com.android.server.wm.flicker.helpers.MultiWindowUtils
import com.android.wm.shell.flicker.service.common.Utils
import com.android.wm.shell.flicker.utils.SplitScreenUtils
import org.junit.After
import org.junit.Assume
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

@Ignore("Base Test Class")
abstract class EnterSplitScreenByDragFromNotification
@JvmOverloads
constructor(val rotation: Rotation = Rotation.ROTATION_0) {
    private val instrumentation: Instrumentation = InstrumentationRegistry.getInstrumentation()
    private val tapl = LauncherInstrumentation()
    private val wmHelper = WindowManagerStateHelper(instrumentation)
    private val device = UiDevice.getInstance(instrumentation)
    private val primaryApp = SplitScreenUtils.getPrimary(instrumentation)
    private val secondaryApp = SplitScreenUtils.getSecondary(instrumentation)
    private val sendNotificationApp = SplitScreenUtils.getSendNotification(instrumentation)

    @Rule @JvmField val testSetupRule = Utils.testSetupRule(NavBar.MODE_GESTURAL, rotation)

    @Before
    fun setup() {
        Assume.assumeTrue(tapl.isTablet)

        MultiWindowUtils.executeShellCommand(
                instrumentation,
                "settings put system notification_cooldown_enabled 0"
        )
        // Send a notification
        sendNotificationApp.launchViaIntent(wmHelper)
        sendNotificationApp.postNotification(wmHelper)
        tapl.goHome()

        tapl.setEnableRotation(true)
        tapl.setExpectedRotation(rotation.value)

        primaryApp.launchViaIntent(wmHelper)
        ChangeDisplayOrientationRule.setRotation(rotation)
    }

    @Test
    open fun enterSplitScreenByDragFromNotification() {
        SplitScreenUtils.dragFromNotificationToSplit(instrumentation, device, wmHelper)
        SplitScreenUtils.waitForSplitComplete(wmHelper, primaryApp, sendNotificationApp)
    }

    @After
    fun teardown() {
        primaryApp.exit(wmHelper)
        secondaryApp.exit(wmHelper)
        sendNotificationApp.exit(wmHelper)

        MultiWindowUtils.executeShellCommand(
                instrumentation,
                "settings reset system notification_cooldown_enabled"
        )
    }
}
