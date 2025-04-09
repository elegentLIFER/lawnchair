

package com.android.wm.shell.flicker.splitscreen.benchmark

import android.tools.NavBar
import android.tools.flicker.junit.FlickerParametersRunnerFactory
import android.tools.flicker.legacy.FlickerBuilder
import android.tools.flicker.legacy.LegacyFlickerTest
import android.tools.flicker.legacy.LegacyFlickerTestFactory
import androidx.test.filters.RequiresDevice
import com.android.wm.shell.flicker.utils.SplitScreenUtils
import org.junit.After
import org.junit.Assume
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.junit.runners.Parameterized

@RequiresDevice
@RunWith(Parameterized::class)
@Parameterized.UseParametersRunnerFactory(FlickerParametersRunnerFactory::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
abstract class EnterSplitScreenByDragFromAllAppsBenchmark(override val flicker: LegacyFlickerTest) :
    SplitScreenBase(flicker) {

    protected val thisTransition: FlickerBuilder.() -> Unit
        get() = {
            setup {
                tapl.goHome()
                primaryApp.launchViaIntent(wmHelper)
                tapl.enableBlockTimeout(true)
            }
            transitions {
                tapl.showTaskbarIfHidden()
                tapl.launchedAppState.taskbar
                    .openAllApps()
                    .getAppIcon(secondaryApp.appName)
                    .dragToSplitscreen(secondaryApp.packageName, primaryApp.packageName)
                SplitScreenUtils.waitForSplitComplete(wmHelper, primaryApp, secondaryApp)
            }
        }

    @Before
    fun before() {
        Assume.assumeTrue(tapl.isTablet)
    }

    @After
    fun after() {
        tapl.enableBlockTimeout(false)
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
