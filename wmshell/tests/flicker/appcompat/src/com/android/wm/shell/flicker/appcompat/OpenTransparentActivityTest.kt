

package com.android.wm.shell.flicker.appcompat

import android.platform.test.annotations.Postsubmit
import android.tools.Rotation
import android.tools.flicker.assertions.FlickerTest
import android.tools.flicker.junit.FlickerParametersRunnerFactory
import android.tools.flicker.legacy.FlickerBuilder
import android.tools.flicker.legacy.LegacyFlickerTest
import android.tools.flicker.legacy.LegacyFlickerTestFactory
import android.tools.traces.component.ComponentNameMatcher
import androidx.test.filters.RequiresDevice
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

/**
 * Test launching app in size compat mode.
 *
 * To run this test: `atest WMShellFlickerTestsOther:OpenTransparentActivityTest`
 *
 * Actions:
 * ```
 *     Launch a letteboxed app and then a transparent activity from it. We test the bounds
 *     are the same.
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
class OpenTransparentActivityTest(flicker: LegacyFlickerTest) : TransparentBaseAppCompat(flicker) {

    /** {@inheritDoc} */
    override val transition: FlickerBuilder.() -> Unit
        get() = {
            setup { letterboxTranslucentLauncherApp.launchViaIntent(wmHelper) }
            transitions {
                waitAndGetLaunchTransparent()?.click() ?: error("Launch Transparent not found")
            }
            teardown {
                letterboxTranslucentApp.exit(wmHelper)
                letterboxTranslucentLauncherApp.exit(wmHelper)
            }
        }

    /** Checks the transparent activity is launched on top of the opaque one */
    @Postsubmit
    @Test
    fun translucentActivityIsLaunchedOnTopOfOpaqueActivity() {
        flicker.assertWm {
            this.isAppWindowOnTop(letterboxTranslucentLauncherApp)
                .then()
                .isAppWindowOnTop(letterboxTranslucentApp)
        }
    }

    /** Checks that the activity is letterboxed */
    @Postsubmit
    @Test
    fun translucentActivityIsLetterboxed() {
        flicker.assertLayers { isVisible(ComponentNameMatcher.LETTERBOX) }
    }

    /** Checks that the translucent activity inherits bounds from the opaque one. */
    @Postsubmit
    @Test
    fun translucentActivityInheritsBoundsFromOpaqueActivity() {
        flicker.assertLayersEnd {
            this.visibleRegion(letterboxTranslucentApp)
                .coversExactly(visibleRegion(letterboxTranslucentLauncherApp).region)
        }
    }

    /** Checks that the translucent activity has rounded corners */
    @Postsubmit
    @Test
    fun translucentActivityHasRoundedCorners() {
        flicker.assertLayersEnd { this.hasRoundedCorners(letterboxTranslucentApp) }
    }

    companion object {
        /**
         * Creates the test configurations.
         *
         * See [FlickerTestFactory.rotationTests] for configuring screen orientation and navigation
         * modes.
         */
        @Parameterized.Parameters(name = "{0}")
        @JvmStatic
        fun getParams(): Collection<FlickerTest> {
            return LegacyFlickerTestFactory.nonRotationTests(
                supportedRotations = listOf(Rotation.ROTATION_90)
            )
        }
    }
}
