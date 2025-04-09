

package com.android.wm.shell.flicker.utils

import android.platform.test.annotations.Presubmit
import android.tools.flicker.legacy.LegacyFlickerTest
import android.tools.traces.component.ComponentNameMatcher
import com.android.server.wm.flicker.entireScreenCovered
import com.android.server.wm.flicker.navBarLayerIsVisibleAtStartAndEnd
import com.android.server.wm.flicker.navBarLayerPositionAtStartAndEnd
import com.android.server.wm.flicker.navBarWindowIsAlwaysVisible
import com.android.server.wm.flicker.statusBarLayerIsVisibleAtStartAndEnd
import com.android.server.wm.flicker.statusBarLayerPositionAtStartAndEnd
import com.android.server.wm.flicker.statusBarWindowIsAlwaysVisible
import com.android.server.wm.flicker.taskBarLayerIsVisibleAtStartAndEnd
import com.android.server.wm.flicker.taskBarWindowIsAlwaysVisible
import org.junit.Assume
import org.junit.Test

interface ICommonAssertions {
    val flicker: LegacyFlickerTest

    /** Checks that all parts of the screen are covered during the transition */
    @Presubmit @Test fun entireScreenCovered() = flicker.entireScreenCovered()

    /**
     * Checks that the [ComponentNameMatcher.NAV_BAR] layer is visible during the whole transition
     */
    @Presubmit
    @Test
    fun navBarLayerIsVisibleAtStartAndEnd() {
        Assume.assumeFalse(flicker.scenario.isTablet)
        flicker.navBarLayerIsVisibleAtStartAndEnd()
    }

    /**
     * Checks the position of the [ComponentNameMatcher.NAV_BAR] at the start and end of the
     * transition
     */
    @Presubmit
    @Test
    fun navBarLayerPositionAtStartAndEnd() {
        Assume.assumeFalse(flicker.scenario.isTablet)
        flicker.navBarLayerPositionAtStartAndEnd()
    }

    /**
     * Checks that the [ComponentNameMatcher.NAV_BAR] window is visible during the whole transition
     *
     * Note: Phones only
     */
    @Presubmit
    @Test
    fun navBarWindowIsAlwaysVisible() {
        Assume.assumeFalse(flicker.scenario.isTablet)
        flicker.navBarWindowIsAlwaysVisible()
    }

    /**
     * Checks that the [ComponentNameMatcher.TASK_BAR] layer is visible during the whole transition
     */
    @Presubmit
    @Test
    fun taskBarLayerIsVisibleAtStartAndEnd() {
        Assume.assumeTrue(flicker.scenario.isTablet)
        flicker.taskBarLayerIsVisibleAtStartAndEnd()
    }

    /**
     * Checks that the [ComponentNameMatcher.TASK_BAR] window is visible during the whole transition
     *
     * Note: Large screen only
     */
    @Presubmit
    @Test
    fun taskBarWindowIsAlwaysVisible() {
        Assume.assumeTrue(flicker.scenario.isTablet)
        flicker.taskBarWindowIsAlwaysVisible()
    }

    /**
     * Checks that the [ComponentNameMatcher.STATUS_BAR] layer is visible during the whole
     * transition
     */
    @Presubmit
    @Test
    fun statusBarLayerIsVisibleAtStartAndEnd() = flicker.statusBarLayerIsVisibleAtStartAndEnd()

    /**
     * Checks the position of the [ComponentNameMatcher.STATUS_BAR] at the start and end of the
     * transition
     */
    @Presubmit
    @Test
    fun statusBarLayerPositionAtStartAndEnd() = flicker.statusBarLayerPositionAtStartAndEnd()

    /**
     * Checks that the [ComponentNameMatcher.STATUS_BAR] window is visible during the whole
     * transition
     */
    @Presubmit @Test fun statusBarWindowIsAlwaysVisible() = flicker.statusBarWindowIsAlwaysVisible()

    /**
     * Checks that all layers that are visible on the trace, are visible for at least 2 consecutive
     * entries.
     */
    @Presubmit
    @Test
    fun visibleLayersShownMoreThanOneConsecutiveEntry() {
        flicker.assertLayers { this.visibleLayersShownMoreThanOneConsecutiveEntry() }
    }

    /**
     * Checks that all windows that are visible on the trace, are visible for at least 2 consecutive
     * entries.
     */
    @Presubmit
    @Test
    fun visibleWindowsShownMoreThanOneConsecutiveEntry() {
        flicker.assertWm { this.visibleWindowsShownMoreThanOneConsecutiveEntry() }
    }
}
