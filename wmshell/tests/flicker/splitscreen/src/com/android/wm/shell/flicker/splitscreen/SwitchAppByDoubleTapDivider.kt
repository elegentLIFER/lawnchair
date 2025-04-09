

package com.android.wm.shell.flicker.splitscreen

import android.platform.test.annotations.Postsubmit
import android.platform.test.annotations.Presubmit
import android.tools.NavBar
import android.tools.flicker.junit.FlickerParametersRunnerFactory
import android.tools.flicker.legacy.FlickerBuilder
import android.tools.flicker.legacy.LegacyFlickerTest
import android.tools.flicker.legacy.LegacyFlickerTestFactory
import androidx.test.filters.RequiresDevice
import com.android.wm.shell.flicker.splitscreen.benchmark.SwitchAppByDoubleTapDividerBenchmark
import com.android.wm.shell.flicker.utils.ICommonAssertions
import com.android.wm.shell.flicker.utils.SPLIT_SCREEN_DIVIDER_COMPONENT
import com.android.wm.shell.flicker.utils.appWindowIsVisibleAtEnd
import com.android.wm.shell.flicker.utils.layerIsVisibleAtEnd
import com.android.wm.shell.flicker.utils.layerKeepVisible
import com.android.wm.shell.flicker.utils.splitAppLayerBoundsIsVisibleAtEnd
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.junit.runners.Parameterized

/**
 * Test double tap the divider bar to switch the two apps.
 *
 * To run this test: `atest WMShellFlickerTestsSplitScreen:SwitchAppByDoubleTapDivider`
 */
@RequiresDevice
@RunWith(Parameterized::class)
@Parameterized.UseParametersRunnerFactory(FlickerParametersRunnerFactory::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class SwitchAppByDoubleTapDivider(override val flicker: LegacyFlickerTest) :
    SwitchAppByDoubleTapDividerBenchmark(flicker), ICommonAssertions {
    override val transition: FlickerBuilder.() -> Unit
        get() = {
            defaultSetup(this)
            defaultTeardown(this)
            thisTransition(this)
        }

    @Presubmit
    @Test
    fun splitScreenDividerKeepVisible() = flicker.layerKeepVisible(SPLIT_SCREEN_DIVIDER_COMPONENT)

    @Presubmit @Test fun primaryAppLayerIsVisibleAtEnd() = flicker.layerIsVisibleAtEnd(primaryApp)

    @Presubmit
    @Test
    fun secondaryAppLayerIsVisibleAtEnd() = flicker.layerIsVisibleAtEnd(secondaryApp)

    @Presubmit
    @Test
    fun primaryAppBoundsIsVisibleAtEnd() =
        flicker.splitAppLayerBoundsIsVisibleAtEnd(
            primaryApp,
            landscapePosLeft = !tapl.isTablet,
            portraitPosTop = true
        )

    @Presubmit
    @Test
    fun secondaryAppBoundsIsVisibleAtEnd() =
        flicker.splitAppLayerBoundsIsVisibleAtEnd(
            secondaryApp,
            landscapePosLeft = tapl.isTablet,
            portraitPosTop = false
        )

    @Presubmit
    @Test
    fun primaryAppWindowIsVisibleAtEnd() = flicker.appWindowIsVisibleAtEnd(primaryApp)

    @Presubmit
    @Test
    fun secondaryAppWindowIsVisibleAtEnd() = flicker.appWindowIsVisibleAtEnd(secondaryApp)

    /** {@inheritDoc} */
    @Postsubmit @Test override fun entireScreenCovered() = super.entireScreenCovered()

    /** {@inheritDoc} */
    @Postsubmit
    @Test
    override fun visibleLayersShownMoreThanOneConsecutiveEntry() =
        super.visibleLayersShownMoreThanOneConsecutiveEntry()

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
