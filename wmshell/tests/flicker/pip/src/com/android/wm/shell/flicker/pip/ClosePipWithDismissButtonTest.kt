

package com.android.wm.shell.flicker.pip

import android.platform.test.annotations.Presubmit
import android.tools.flicker.junit.FlickerParametersRunnerFactory
import android.tools.flicker.legacy.FlickerBuilder
import android.tools.flicker.legacy.LegacyFlickerTest
import com.android.wm.shell.flicker.pip.common.ClosePipTransition
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.junit.runners.Parameterized

/**
 * Test closing a pip window via the dismiss button
 *
 * To run this test: `atest WMShellFlickerTestsPip1:ClosePipWithDismissButtonTest`
 *
 * Actions:
 * ```
 *     Launch an app in pip mode [pipApp],
 *     Click on the pip window
 *     Click on dismiss button and wait window disappear
 * ```
 *
 * Notes:
 * ```
 *     1. Some default assertions (e.g., nav bar, status bar and screen covered)
 *        are inherited [PipTransition]
 *     2. Part of the test setup occurs automatically via
 *        [android.tools.flicker.legacy.runner.TransitionRunner],
 *        including configuring navigation mode, initial orientation and ensuring no
 *        apps are running before setup
 * ```
 */
@RunWith(Parameterized::class)
@Parameterized.UseParametersRunnerFactory(FlickerParametersRunnerFactory::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ClosePipWithDismissButtonTest(flicker: LegacyFlickerTest) : ClosePipTransition(flicker) {
    override val thisTransition: FlickerBuilder.() -> Unit = {
        transitions { pipApp.closePipWindow(wmHelper) }
    }

    /**
     * Checks that the focus changes between the pip menu window and the launcher when clicking the
     * dismiss button on pip menu to close the pip window.
     */
    @Presubmit
    @Test
    fun focusChanges() {
        flicker.assertEventLog { this.focusChanges("PipMenuView", "NexusLauncherActivity") }
    }
}
