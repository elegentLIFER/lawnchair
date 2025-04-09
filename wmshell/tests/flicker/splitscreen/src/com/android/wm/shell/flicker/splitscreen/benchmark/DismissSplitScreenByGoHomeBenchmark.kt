

package com.android.wm.shell.flicker.splitscreen.benchmark

import android.tools.flicker.junit.FlickerParametersRunnerFactory
import android.tools.flicker.legacy.FlickerBuilder
import android.tools.flicker.legacy.LegacyFlickerTest
import android.tools.flicker.legacy.LegacyFlickerTestFactory
import androidx.test.filters.RequiresDevice
import com.android.wm.shell.flicker.utils.SplitScreenUtils
import org.junit.FixMethodOrder
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.junit.runners.Parameterized

@RequiresDevice
@RunWith(Parameterized::class)
@Parameterized.UseParametersRunnerFactory(FlickerParametersRunnerFactory::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
abstract class DismissSplitScreenByGoHomeBenchmark(override val flicker: LegacyFlickerTest) :
    SplitScreenBase(flicker) {
    protected val thisTransition: FlickerBuilder.() -> Unit
        get() = {
            setup {
                SplitScreenUtils.enterSplit(
                    wmHelper,
                    tapl,
                    device,
                    primaryApp,
                    secondaryApp,
                    flicker.scenario.startRotation
                )
            }
            transitions {
                tapl.goHome()
                wmHelper.StateSyncBuilder().withHomeActivityVisible().waitForAndVerify()
            }
        }

    companion object {
        @Parameterized.Parameters(name = "{0}")
        @JvmStatic
        fun getParams() = LegacyFlickerTestFactory.nonRotationTests()
    }
}
