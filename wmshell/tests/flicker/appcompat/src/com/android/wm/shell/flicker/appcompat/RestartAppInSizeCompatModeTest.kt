

package com.android.wm.shell.flicker.appcompat

import android.platform.test.annotations.Postsubmit
import android.tools.flicker.assertions.FlickerTest
import android.tools.flicker.junit.FlickerParametersRunnerFactory
import android.tools.flicker.legacy.FlickerBuilder
import android.tools.flicker.legacy.LegacyFlickerTest
import android.tools.flicker.legacy.LegacyFlickerTestFactory
import android.tools.helpers.WindowUtils
import androidx.test.filters.RequiresDevice
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

/**
 * Test restarting app in size compat mode.
 *
 * To run this test: `atest WMShellFlickerTestsOther:RestartAppInSizeCompatModeTest`
 *
 * Actions:
 * ```
 *     Rotate app to opposite orientation to trigger size compat mode
 *     Press restart button and wait for letterboxed app to resize
 * ```
 *
 * Notes:
 * ```
 *     Some default assertions (e.g., nav bar, status bar and screen covered)
 *     are inherited [BaseTest]
 * ```
 */
@RequiresDevice
@RunWith(Parameterized::class)
@Parameterized.UseParametersRunnerFactory(FlickerParametersRunnerFactory::class)
class RestartAppInSizeCompatModeTest(flicker: LegacyFlickerTest) : BaseAppCompat(flicker) {

    /** {@inheritDoc} */
    override val transition: FlickerBuilder.() -> Unit
        get() = {
            super.transition(this)
            transitions { letterboxApp.clickRestart(wmHelper) }
        }

    @Postsubmit @Test fun appVisibleAtStartAndEnd() = assertLetterboxAppVisibleAtStartAndEnd()

    @Postsubmit
    @Test
    fun appWindowVisibilityChanges() {
        flicker.assertWm {
            this.isAppWindowVisible(letterboxApp)
                .then()
                .isAppWindowInvisible(letterboxApp) // animatingExit true
                .then()
                .isAppWindowVisible(letterboxApp) // Activity finish relaunching
        }
    }

    @Postsubmit @Test fun appLayerKeepVisible() = assertLetterboxAppLayerKeepVisible()

    @Postsubmit @Test fun appIsLetterboxedAtStart() = assertAppLetterboxedAtStart()

    @Postsubmit
    @Test
    fun letterboxedAppHasRoundedCorners() = assertLetterboxAppAtStartHasRoundedCorners()

    /** Checks that the visible region of [letterboxApp] is still within display bounds */
    @Postsubmit
    @Test
    fun appWindowRemainInsideVisibleBounds() {
        val displayBounds = WindowUtils.getDisplayBounds(flicker.scenario.endRotation)
        flicker.assertLayersEnd { visibleRegion(letterboxApp).coversAtMost(displayBounds) }
    }

    companion object {
        /**
         * Creates the test configurations.
         *
         * See [LegacyFlickerTestFactory.rotationTests] for configuring screen orientation and
         * navigation modes.
         */
        @Parameterized.Parameters(name = "{0}")
        @JvmStatic
        fun getParams(): Collection<FlickerTest> {
            return LegacyFlickerTestFactory.rotationTests()
        }
    }
}
