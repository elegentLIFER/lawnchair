

package com.android.wm.shell.flicker.bubble

import android.platform.test.annotations.Presubmit
import android.tools.flicker.junit.FlickerParametersRunnerFactory
import android.tools.flicker.legacy.FlickerBuilder
import android.tools.flicker.legacy.LegacyFlickerTest
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

/**
 * Test creating a bubble notification
 *
 * To run this test: `atest WMShellFlickerTestsBubbles:SendBubbleNotificationTest`
 *
 * Actions:
 * ```
 *     Launch an app and enable app's bubble notification
 *     Send a bubble notification
 * ```
 */
@RunWith(Parameterized::class)
@Parameterized.UseParametersRunnerFactory(FlickerParametersRunnerFactory::class)
class SendBubbleNotificationTest(flicker: LegacyFlickerTest) : BaseBubbleScreen(flicker) {

    /** {@inheritDoc} */
    override val transition: FlickerBuilder.() -> Unit
        get() = buildTransition {
            transitions {
                val addBubbleBtn = waitAndGetAddBubbleBtn()
                addBubbleBtn?.click() ?: error("Bubble widget not found")

                device.wait(
                    Until.findObjects(By.res(SYSTEM_UI_PACKAGE, BUBBLE_RES_NAME)),
                    FIND_OBJECT_TIMEOUT
                )
                    ?: error("No bubbles found")
                device.waitForIdle()
            }
        }

    @Presubmit
    @Test
    open fun testAppIsAlwaysVisible() {
        flicker.assertLayers { this.isVisible(testApp) }
    }
}
