

package com.android.wm.shell.flicker.pip

import android.tools.flicker.junit.FlickerParametersRunnerFactory
import android.tools.flicker.legacy.FlickerBuilder
import android.tools.flicker.legacy.LegacyFlickerTest
import com.android.wm.shell.flicker.pip.common.ExitPipToAppTransition
import org.junit.FixMethodOrder
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.junit.runners.Parameterized

/**
 * Test expanding a pip window back to full screen via an intent
 *
 * To run this test: `atest WMShellFlickerTestsPip2:ExitPipToAppViaIntentTest`
 *
 * Actions:
 * ```
 *     Launch an app in pip mode [pipApp],
 *     Launch another full screen mode [testApp]
 *     Expand [pipApp] app to full screen via an intent
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
class ExitPipToAppViaIntentTest(flicker: LegacyFlickerTest) : ExitPipToAppTransition(flicker) {
    override val thisTransition: FlickerBuilder.() -> Unit = {
        setup {
            // launch an app behind the pip one
            testApp.launchViaIntent(wmHelper)
        }
        transitions {
            // This will bring PipApp to fullscreen
            pipApp.exitPipToFullScreenViaIntent(wmHelper)
            // Wait until the other app is no longer visible
            wmHelper.StateSyncBuilder().withWindowSurfaceDisappeared(testApp).waitForAndVerify()
        }
    }
}
