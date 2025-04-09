

package com.android.wm.shell.flicker.splitscreen.benchmark

import android.tools.NavBar
import android.tools.Rotation
import android.tools.flicker.junit.FlickerParametersRunnerFactory
import android.tools.flicker.legacy.FlickerBuilder
import android.tools.flicker.legacy.LegacyFlickerTest
import android.tools.flicker.legacy.LegacyFlickerTestFactory
import android.tools.helpers.WindowUtils
import android.tools.traces.parsers.WindowManagerStateHelper
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
abstract class SwitchAppByDoubleTapDividerBenchmark(override val flicker: LegacyFlickerTest) :
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
                SplitScreenUtils.doubleTapDividerToSwitch(device)
                wmHelper.StateSyncBuilder().withAppTransitionIdle().waitForAndVerify()

                waitForLayersToSwitch(wmHelper)
                waitForWindowsToSwitch(wmHelper)
            }
        }

    private fun waitForWindowsToSwitch(wmHelper: WindowManagerStateHelper) {
        wmHelper
            .StateSyncBuilder()
            .add("appWindowsSwitched") {
                val primaryAppWindow =
                    it.wmState.visibleWindows.firstOrNull { window ->
                        primaryApp.windowMatchesAnyOf(window)
                    }
                        ?: return@add false
                val secondaryAppWindow =
                    it.wmState.visibleWindows.firstOrNull { window ->
                        secondaryApp.windowMatchesAnyOf(window)
                    }
                        ?: return@add false

                if (isLandscape(flicker.scenario.endRotation)) {
                    return@add if (flicker.scenario.isTablet) {
                        secondaryAppWindow.frame.right <= primaryAppWindow.frame.left
                    } else {
                        primaryAppWindow.frame.right <= secondaryAppWindow.frame.left
                    }
                } else {
                    return@add if (flicker.scenario.isTablet) {
                        primaryAppWindow.frame.bottom <= secondaryAppWindow.frame.top
                    } else {
                        primaryAppWindow.frame.bottom <= secondaryAppWindow.frame.top
                    }
                }
            }
            .waitForAndVerify()
    }

    private fun waitForLayersToSwitch(wmHelper: WindowManagerStateHelper) {
        wmHelper
            .StateSyncBuilder()
            .add("appLayersSwitched") {
                val primaryAppLayer =
                    it.layerState.visibleLayers.firstOrNull { window ->
                        primaryApp.layerMatchesAnyOf(window)
                    }
                        ?: return@add false
                val secondaryAppLayer =
                    it.layerState.visibleLayers.firstOrNull { window ->
                        secondaryApp.layerMatchesAnyOf(window)
                    }
                        ?: return@add false

                val primaryVisibleRegion = primaryAppLayer.visibleRegion?.bounds ?: return@add false
                val secondaryVisibleRegion =
                    secondaryAppLayer.visibleRegion?.bounds ?: return@add false

                if (isLandscape(flicker.scenario.endRotation)) {
                    return@add if (flicker.scenario.isTablet) {
                        secondaryVisibleRegion.right <= primaryVisibleRegion.left
                    } else {
                        primaryVisibleRegion.right <= secondaryVisibleRegion.left
                    }
                } else {
                    return@add if (flicker.scenario.isTablet) {
                        primaryVisibleRegion.bottom <= secondaryVisibleRegion.top
                    } else {
                        primaryVisibleRegion.bottom <= secondaryVisibleRegion.top
                    }
                }
            }
            .waitForAndVerify()
    }

    private fun isLandscape(rotation: Rotation): Boolean {
        val displayBounds = WindowUtils.getDisplayBounds(rotation)
        return displayBounds.width() > displayBounds.height()
    }

    companion object {
        @Parameterized.Parameters(name = "{0}")
        @JvmStatic
        fun getParams() =
            LegacyFlickerTestFactory.nonRotationTests(
                // TODO(b/176061063):The 3 buttons of nav bar do not exist in the hierarchy.
                supportedNavigationModes = listOf(NavBar.MODE_GESTURAL)
            )
    }
}
