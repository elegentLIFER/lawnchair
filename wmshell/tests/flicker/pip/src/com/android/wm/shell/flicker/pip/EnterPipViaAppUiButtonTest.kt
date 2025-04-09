

package com.android.wm.shell.flicker.pip

import android.tools.flicker.junit.FlickerParametersRunnerFactory
import android.tools.flicker.legacy.FlickerBuilder
import android.tools.flicker.legacy.LegacyFlickerTest
import com.android.wm.shell.flicker.pip.common.EnterPipTransition
import org.junit.FixMethodOrder
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.junit.runners.Parameterized

/**
 * Test entering pip from an app by interacting with the app UI
 *
 * To run this test: `atest WMShellFlickerTestsPip2:EnterPipViaAppUiButtonTest`
 *
 * Actions:
 * ```
 *     Launch an app in full screen
 *     Press an "enter pip" button to put [pipApp] in pip mode
 * ```
 *
 * Notes:
 * ```
 *     1. Some default assertions (e.g., nav bar, status bar and screen covered)
 *        are inherited from [PipTransition]
 *     2. Part of the test setup occurs automatically via
 *        [android.tools.flicker.legacy.runner.TransitionRunner],
 *        including configuring navigation mode, initial orientation and ensuring no
 *        apps are running before setup
 * ```
 */
@RunWith(Parameterized::class)
@Parameterized.UseParametersRunnerFactory(FlickerParametersRunnerFactory::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
open class EnterPipViaAppUiButtonTest(flicker: LegacyFlickerTest) : EnterPipTransition(flicker) {
    override val thisTransition: FlickerBuilder.() -> Unit = {
        transitions { pipApp.clickEnterPipButton(wmHelper) }
    }
}
