

package com.android.wm.shell.flicker.splitscreen

import android.platform.test.annotations.Postsubmit
import android.platform.test.annotations.Presubmit
import android.tools.flicker.junit.FlickerParametersRunnerFactory
import android.tools.flicker.legacy.FlickerBuilder
import android.tools.flicker.legacy.LegacyFlickerTest
import android.tools.helpers.WindowUtils
import androidx.test.filters.FlakyTest
import androidx.test.filters.RequiresDevice
import com.android.wm.shell.flicker.splitscreen.benchmark.DismissSplitScreenByDividerBenchmark
import com.android.wm.shell.flicker.utils.ICommonAssertions
import com.android.wm.shell.flicker.utils.appWindowBecomesInvisible
import com.android.wm.shell.flicker.utils.appWindowIsVisibleAtEnd
import com.android.wm.shell.flicker.utils.layerBecomesInvisible
import com.android.wm.shell.flicker.utils.layerIsVisibleAtEnd
import com.android.wm.shell.flicker.utils.splitAppLayerBoundsBecomesInvisible
import com.android.wm.shell.flicker.utils.splitScreenDividerBecomesInvisible
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.junit.runners.Parameterized

/**
 * Test dismiss split screen by dragging the divider bar.
 *
 * To run this test: `atest WMShellFlickerTestsSplitScreen:DismissSplitScreenByDivider`
 */
@RequiresDevice
@RunWith(Parameterized::class)
@Parameterized.UseParametersRunnerFactory(FlickerParametersRunnerFactory::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class DismissSplitScreenByDivider(override val flicker: LegacyFlickerTest) :
    DismissSplitScreenByDividerBenchmark(flicker), ICommonAssertions {

    override val transition: FlickerBuilder.() -> Unit
        get() = {
            defaultSetup(this)
            defaultTeardown(this)
            thisTransition(this)
        }

    @Presubmit
    @Test
    fun splitScreenDividerBecomesInvisible() = flicker.splitScreenDividerBecomesInvisible()

    @Presubmit
    @Test
    fun primaryAppLayerBecomesInvisible() = flicker.layerBecomesInvisible(primaryApp)

    @Presubmit
    @Test
    fun secondaryAppLayerIsVisibleAtEnd() = flicker.layerIsVisibleAtEnd(secondaryApp)

    @Presubmit
    @Test
    fun primaryAppBoundsBecomesInvisible() =
        flicker.splitAppLayerBoundsBecomesInvisible(
            primaryApp,
            landscapePosLeft = tapl.isTablet,
            portraitPosTop = false
        )

    @Presubmit
    @Test
    fun secondaryAppBoundsIsFullscreenAtEnd() {
        flicker.assertLayersEnd {
            val displayBounds = WindowUtils.getDisplayBounds(flicker.scenario.endRotation)
            visibleRegion(secondaryApp).coversExactly(displayBounds)
        }
    }

    @Presubmit
    @Test
    fun primaryAppWindowBecomesInvisible() = flicker.appWindowBecomesInvisible(primaryApp)

    @Presubmit
    @Test
    fun secondaryAppWindowIsVisibleAtEnd() = flicker.appWindowIsVisibleAtEnd(secondaryApp)

    /** {@inheritDoc} */
    @Postsubmit @Test override fun entireScreenCovered() = super.entireScreenCovered()

    /** {@inheritDoc} */
    @Postsubmit
    @Test
    override fun navBarLayerIsVisibleAtStartAndEnd() = super.navBarLayerIsVisibleAtStartAndEnd()

    /** {@inheritDoc} */
    @FlakyTest(bugId = 206753786)
    @Test
    override fun navBarLayerPositionAtStartAndEnd() = super.navBarLayerPositionAtStartAndEnd()

    /** {@inheritDoc} */
    @Postsubmit
    @Test
    override fun navBarWindowIsAlwaysVisible() = super.navBarWindowIsAlwaysVisible()

    /** {@inheritDoc} */
    @Postsubmit
    @Test
    override fun statusBarLayerIsVisibleAtStartAndEnd() =
        super.statusBarLayerIsVisibleAtStartAndEnd()

    /** {@inheritDoc} */
    @Postsubmit
    @Test
    override fun statusBarLayerPositionAtStartAndEnd() = super.statusBarLayerPositionAtStartAndEnd()

    /** {@inheritDoc} */
    @Postsubmit
    @Test
    override fun statusBarWindowIsAlwaysVisible() = super.statusBarWindowIsAlwaysVisible()

    /** {@inheritDoc} */
    @Postsubmit
    @Test
    override fun taskBarLayerIsVisibleAtStartAndEnd() = super.taskBarLayerIsVisibleAtStartAndEnd()

    /** {@inheritDoc} */
    @Postsubmit
    @Test
    override fun taskBarWindowIsAlwaysVisible() = super.taskBarWindowIsAlwaysVisible()

    /** {@inheritDoc} */
    @Postsubmit
    @Test
    override fun visibleLayersShownMoreThanOneConsecutiveEntry() =
        super.visibleLayersShownMoreThanOneConsecutiveEntry()

    /** {@inheritDoc} */
    @Postsubmit
    @Test
    override fun visibleWindowsShownMoreThanOneConsecutiveEntry() =
        super.visibleWindowsShownMoreThanOneConsecutiveEntry()
}
