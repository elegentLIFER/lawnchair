

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
abstract class SwitchBetweenSplitPairsBenchmark(override val flicker: LegacyFlickerTest) :
    SplitScreenBase(flicker) {
    protected val thirdApp = SplitScreenUtils.getIme(instrumentation)
    protected val fourthApp = SplitScreenUtils.getSendNotification(instrumentation)

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
                SplitScreenUtils.enterSplit(
                    wmHelper,
                    tapl,
                    device,
                    thirdApp,
                    fourthApp,
                    flicker.scenario.startRotation
                )
                SplitScreenUtils.waitForSplitComplete(wmHelper, thirdApp, fourthApp)
            }
            transitions {
                tapl.launchedAppState.quickSwitchToPreviousApp()
                SplitScreenUtils.waitForSplitComplete(wmHelper, primaryApp, secondaryApp)
            }
            teardown {
                thirdApp.exit(wmHelper)
                fourthApp.exit(wmHelper)
            }
        }

    override val transition: FlickerBuilder.() -> Unit
        get() = {
            defaultSetup(this)
            defaultTeardown(this)
            thisTransition(this)
        }

    companion object {
        @Parameterized.Parameters(name = "{0}")
        @JvmStatic
        fun getParams() = LegacyFlickerTestFactory.nonRotationTests()
    }
}
