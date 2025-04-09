

package com.android.wm.shell.flicker.splitscreen

import android.platform.test.annotations.Presubmit
import android.tools.Rotation
import android.tools.flicker.junit.FlickerParametersRunnerFactory
import android.tools.flicker.legacy.FlickerBuilder
import android.tools.flicker.legacy.LegacyFlickerTest
import android.tools.flicker.legacy.LegacyFlickerTestFactory
import android.tools.traces.component.ComponentNameMatcher
import androidx.test.filters.RequiresDevice
import com.android.wm.shell.flicker.splitscreen.benchmark.MultipleShowImeRequestsInSplitScreenBenchmark
import com.android.wm.shell.flicker.utils.ICommonAssertions
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.junit.runners.Parameterized

/**
 * Test quick switch between two split pairs.
 *
 * To run this test: `atest WMShellFlickerTestsSplitScreenGroup2:MultipleShowImeRequestsInSplitScreen`
 */
@RequiresDevice
@RunWith(Parameterized::class)
@Parameterized.UseParametersRunnerFactory(FlickerParametersRunnerFactory::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class MultipleShowImeRequestsInSplitScreen(override val flicker: LegacyFlickerTest) :
        MultipleShowImeRequestsInSplitScreenBenchmark(flicker), ICommonAssertions {
    override val transition: FlickerBuilder.() -> Unit
        get() = {
            defaultSetup(this)
            defaultTeardown(this)
            thisTransition(this)
        }

    @Presubmit
    @Test
    fun imeLayerAlwaysVisible() =
            flicker.assertLayers {
                this.isVisible(ComponentNameMatcher.IME)
            }

    companion object {
        @Parameterized.Parameters(name = "{0}")
        @JvmStatic
        fun getParams() = LegacyFlickerTestFactory.nonRotationTests(
                supportedRotations = listOf(Rotation.ROTATION_0)
        )
    }
}
