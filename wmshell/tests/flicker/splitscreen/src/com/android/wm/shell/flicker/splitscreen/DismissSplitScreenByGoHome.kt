

package com.android.wm.shell.flicker.splitscreen

import android.platform.test.annotations.Presubmit
import android.tools.flicker.junit.FlickerParametersRunnerFactory
import android.tools.flicker.legacy.FlickerBuilder
import android.tools.flicker.legacy.LegacyFlickerTest
import android.tools.flicker.legacy.LegacyFlickerTestFactory
import androidx.test.filters.FlakyTest
import androidx.test.filters.RequiresDevice
import com.android.wm.shell.flicker.splitscreen.benchmark.DismissSplitScreenByGoHomeBenchmark
import com.android.wm.shell.flicker.utils.ICommonAssertions
import com.android.wm.shell.flicker.utils.appWindowBecomesInvisible
import com.android.wm.shell.flicker.utils.layerBecomesInvisible
import com.android.wm.shell.flicker.utils.splitAppLayerBoundsBecomesInvisible
import com.android.wm.shell.flicker.utils.splitScreenDividerBecomesInvisible
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.junit.runners.Parameterized

/**
 * Test dismiss split screen by go home.
 *
 * To run this test: `atest WMShellFlickerTestsSplitScreen:DismissSplitScreenByGoHome`
 */
@RequiresDevice
@RunWith(Parameterized::class)
@Parameterized.UseParametersRunnerFactory(FlickerParametersRunnerFactory::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class DismissSplitScreenByGoHome(override val flicker: LegacyFlickerTest) :
    DismissSplitScreenByGoHomeBenchmark(flicker), ICommonAssertions {
    override val transition: FlickerBuilder.() -> Unit
        get() = {
            defaultSetup(this)
            defaultTeardown(this)
            thisTransition(this)
        }

    @Presubmit
    @Test
    fun splitScreenDividerBecomesInvisible() = flicker.splitScreenDividerBecomesInvisible()

    @FlakyTest(bugId = 241525302)
    @Test
    fun primaryAppLayerBecomesInvisible() = flicker.layerBecomesInvisible(primaryApp)

    // TODO(b/245472831): Move back to presubmit after shell transitions landing.
    @FlakyTest(bugId = 245472831)
    @Test
    fun secondaryAppLayerBecomesInvisible() = flicker.layerBecomesInvisible(secondaryApp)

    // TODO(b/245472831): Move back to presubmit after shell transitions landing.
    @FlakyTest(bugId = 245472831)
    @Test
    fun primaryAppBoundsBecomesInvisible() =
        flicker.splitAppLayerBoundsBecomesInvisible(
            primaryApp,
            landscapePosLeft = tapl.isTablet,
            portraitPosTop = false
        )

    @FlakyTest(bugId = 250530241)
    @Test
    fun secondaryAppBoundsBecomesInvisible() =
        flicker.splitAppLayerBoundsBecomesInvisible(
            secondaryApp,
            landscapePosLeft = !tapl.isTablet,
            portraitPosTop = true
        )

    @Presubmit
    @Test
    fun primaryAppWindowBecomesInvisible() = flicker.appWindowBecomesInvisible(primaryApp)

    @Presubmit
    @Test
    fun secondaryAppWindowBecomesInvisible() = flicker.appWindowBecomesInvisible(secondaryApp)

    /** {@inheritDoc} */
    @FlakyTest(bugId = 251268711)
    @Test
    override fun entireScreenCovered() = super.entireScreenCovered()

    /** {@inheritDoc} */
    @Presubmit
    @Test
    override fun navBarLayerIsVisibleAtStartAndEnd() = super.navBarLayerIsVisibleAtStartAndEnd()

    /** {@inheritDoc} */
    @FlakyTest(bugId = 206753786)
    @Test
    override fun navBarLayerPositionAtStartAndEnd() = super.navBarLayerPositionAtStartAndEnd()

    /** {@inheritDoc} */
    @Presubmit
    @Test
    override fun navBarWindowIsAlwaysVisible() = super.navBarWindowIsAlwaysVisible()

    /** {@inheritDoc} */
    @Presubmit
    @Test
    override fun statusBarLayerIsVisibleAtStartAndEnd() =
        super.statusBarLayerIsVisibleAtStartAndEnd()

    /** {@inheritDoc} */
    @Presubmit
    @Test
    override fun statusBarLayerPositionAtStartAndEnd() = super.statusBarLayerPositionAtStartAndEnd()

    /** {@inheritDoc} */
    @Presubmit
    @Test
    override fun statusBarWindowIsAlwaysVisible() = super.statusBarWindowIsAlwaysVisible()

    /** {@inheritDoc} */
    @Presubmit
    @Test
    override fun taskBarLayerIsVisibleAtStartAndEnd() = super.taskBarLayerIsVisibleAtStartAndEnd()

    /** {@inheritDoc} */
    @Presubmit
    @Test
    override fun taskBarWindowIsAlwaysVisible() = super.taskBarWindowIsAlwaysVisible()

    /** {@inheritDoc} */
    @FlakyTest
    @Test
    override fun visibleLayersShownMoreThanOneConsecutiveEntry() =
        super.visibleLayersShownMoreThanOneConsecutiveEntry()

    /** {@inheritDoc} */
    @Presubmit
    @Test
    override fun visibleWindowsShownMoreThanOneConsecutiveEntry() =
        super.visibleWindowsShownMoreThanOneConsecutiveEntry()

    companion object {
        @Parameterized.Parameters(name = "{0}")
        @JvmStatic
        fun getParams() = LegacyFlickerTestFactory.nonRotationTests()
    }
}
