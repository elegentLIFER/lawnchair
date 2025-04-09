

package com.android.wm.shell.flicker.splitscreen.benchmark

import android.tools.flicker.junit.FlickerParametersRunnerFactory
import android.tools.flicker.legacy.FlickerBuilder
import android.tools.flicker.legacy.LegacyFlickerTest
import android.tools.flicker.legacy.LegacyFlickerTestFactory
import android.tools.traces.component.ComponentNameMatcher
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
abstract class CopyContentInSplitBenchmark(override val flicker: LegacyFlickerTest) :
    SplitScreenBase(flicker) {
    protected val textEditApp = SplitScreenUtils.getIme(instrumentation)
    protected val magnifierLayer = ComponentNameMatcher("", "magnifier surface bbq wrapper#")
    protected val popupWindowLayer = ComponentNameMatcher("", "PopupWindow:")
    protected val thisTransition: FlickerBuilder.() -> Unit
        get() = {
            setup {
                SplitScreenUtils.enterSplit(
                    wmHelper,
                    tapl,
                    device,
                    primaryApp,
                    textEditApp,
                    flicker.scenario.startRotation
                )
            }
            transitions {
                SplitScreenUtils.copyContentInSplit(
                    instrumentation,
                    device,
                    primaryApp,
                    textEditApp
                )
            }
        }

    companion object {
        @Parameterized.Parameters(name = "{0}")
        @JvmStatic
        fun getParams() = LegacyFlickerTestFactory.nonRotationTests()
    }
}
