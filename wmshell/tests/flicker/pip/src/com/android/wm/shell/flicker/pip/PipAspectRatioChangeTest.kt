

package com.android.wm.shell.flicker.pip

import android.platform.test.annotations.Presubmit
import android.tools.Rotation
import android.tools.flicker.junit.FlickerParametersRunnerFactory
import android.tools.flicker.legacy.FlickerBuilder
import android.tools.flicker.legacy.LegacyFlickerTest
import android.tools.flicker.legacy.LegacyFlickerTestFactory
import com.android.wm.shell.flicker.pip.common.PipTransition
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.junit.runners.Parameterized

/** Test changing aspect ratio of pip. */
@RunWith(Parameterized::class)
@Parameterized.UseParametersRunnerFactory(FlickerParametersRunnerFactory::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class PipAspectRatioChangeTest(flicker: LegacyFlickerTest) : PipTransition(flicker) {
    override val thisTransition: FlickerBuilder.() -> Unit = {
        transitions { pipApp.changeAspectRatio() }
    }

    @Presubmit
    @Test
    fun pipAspectRatioChangesProperly() {
        flicker.assertLayersStart { this.visibleRegion(pipApp).isSameAspectRatio(16, 9) }
        flicker.assertLayersEnd { this.visibleRegion(pipApp).isSameAspectRatio(1, 2) }
    }

    companion object {
        /**
         * Creates the test configurations.
         *
         * See [LegacyFlickerTestFactory.nonRotationTests] for configuring screen orientation and
         * navigation modes.
         */
        @Parameterized.Parameters(name = "{0}")
        @JvmStatic
        fun getParams() =
            LegacyFlickerTestFactory.nonRotationTests(
                supportedRotations = listOf(Rotation.ROTATION_0)
            )
    }
}
