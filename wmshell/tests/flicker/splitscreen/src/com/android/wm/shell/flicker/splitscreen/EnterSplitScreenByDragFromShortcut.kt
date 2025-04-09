

package com.android.wm.shell.flicker.splitscreen

import android.platform.test.annotations.Presubmit
import android.tools.NavBar
import android.tools.flicker.junit.FlickerParametersRunnerFactory
import android.tools.flicker.legacy.FlickerBuilder
import android.tools.flicker.legacy.LegacyFlickerTest
import android.tools.flicker.legacy.LegacyFlickerTestFactory
import androidx.test.filters.FlakyTest
import androidx.test.filters.RequiresDevice
import com.android.wm.shell.flicker.splitscreen.benchmark.EnterSplitScreenByDragFromShortcutBenchmark
import com.android.wm.shell.flicker.utils.ICommonAssertions
import com.android.wm.shell.flicker.utils.appWindowIsVisibleAtEnd
import com.android.wm.shell.flicker.utils.layerBecomesVisible
import com.android.wm.shell.flicker.utils.layerIsVisibleAtEnd
import com.android.wm.shell.flicker.utils.splitAppLayerBoundsBecomesVisibleByDrag
import com.android.wm.shell.flicker.utils.splitAppLayerBoundsIsVisibleAtEnd
import com.android.wm.shell.flicker.utils.splitScreenDividerBecomesVisible
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.junit.runners.Parameterized

/**
 * Test enter split screen by dragging a shortcut. This test is only for large screen devices.
 *
 * To run this test: `atest WMShellFlickerTestsSplitScreen:EnterSplitScreenByDragFromShortcut`
 */
@RequiresDevice
@RunWith(Parameterized::class)
@Parameterized.UseParametersRunnerFactory(FlickerParametersRunnerFactory::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class EnterSplitScreenByDragFromShortcut(override val flicker: LegacyFlickerTest) :
    EnterSplitScreenByDragFromShortcutBenchmark(flicker), ICommonAssertions {

    override val transition: FlickerBuilder.() -> Unit
        get() = {
            defaultSetup(this)
            defaultTeardown(this)
            thisTransition(this)
        }

    @Presubmit
    @Test
    fun splitScreenDividerBecomesVisible() = flicker.splitScreenDividerBecomesVisible()

    @Presubmit @Test fun primaryAppLayerIsVisibleAtEnd() = flicker.layerIsVisibleAtEnd(primaryApp)

    @Presubmit
    @Test
    fun secondaryAppLayerBecomesVisible() = flicker.layerBecomesVisible(secondaryApp)

    @Presubmit
    @Test
    fun primaryAppBoundsIsVisibleAtEnd() =
        flicker.splitAppLayerBoundsIsVisibleAtEnd(
            primaryApp,
            landscapePosLeft = false,
            portraitPosTop = false
        )

    @Presubmit
    @Test
    fun secondaryAppBoundsBecomesVisible() =
        flicker.splitAppLayerBoundsBecomesVisibleByDrag(secondaryApp)

    @Presubmit
    @Test
    fun primaryAppWindowIsVisibleAtEnd() = flicker.appWindowIsVisibleAtEnd(primaryApp)

    @Presubmit
    @Test
    fun secondaryAppWindowBecomesVisible() {
        flicker.assertWm {
            this.notContains(secondaryApp)
                .then()
                .isAppWindowInvisible(secondaryApp, isOptional = true)
                .then()
                .isAppWindowVisible(secondaryApp)
        }
    }

    /** {@inheritDoc} */
    @FlakyTest(bugId = 241523824)
    @Test
    override fun entireScreenCovered() = super.entireScreenCovered()

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
