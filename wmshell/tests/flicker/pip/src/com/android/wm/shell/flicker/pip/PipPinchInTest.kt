

package com.android.wm.shell.flicker.pip

import android.platform.test.annotations.Presubmit
import android.tools.Rotation
import android.tools.flicker.junit.FlickerParametersRunnerFactory
import android.tools.flicker.legacy.FlickerBuilder
import android.tools.flicker.legacy.LegacyFlickerTest
import android.tools.flicker.legacy.LegacyFlickerTestFactory
import android.tools.flicker.subject.exceptions.IncorrectRegionException
import androidx.test.filters.RequiresDevice
import com.android.wm.shell.flicker.pip.common.PipTransition
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.junit.runners.Parameterized

/** Test minimizing a pip window via pinch in gesture. */
@RequiresDevice
@RunWith(Parameterized::class)
@Parameterized.UseParametersRunnerFactory(FlickerParametersRunnerFactory::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class PipPinchInTest(flicker: LegacyFlickerTest) : PipTransition(flicker) {
    override val thisTransition: FlickerBuilder.() -> Unit = {
        transitions { pipApp.pinchInPipWindow(wmHelper, 0.4f, 30) }
    }

    /**
     * Checks that the visible region area of [pipApp] decreases and then increases during the
     * animation.
     */
    @Presubmit
    @Test
    fun pipLayerAreaDecreasesThenIncreases() {
        val isAreaDecreasing = arrayOf(true)
        flicker.assertLayers {
            val pipLayerList = this.layers { pipApp.layerMatchesAnyOf(it) && it.isVisible }
            pipLayerList.zipWithNext { previous, current ->
                if (isAreaDecreasing[0]) {
                    try {
                        current.visibleRegion.notBiggerThan(previous.visibleRegion.region)
                    } catch (e: IncorrectRegionException) {
                        isAreaDecreasing[0] = false
                    }
                } else {
                    previous.visibleRegion.notBiggerThan(current.visibleRegion.region)
                }
            }
        }
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
