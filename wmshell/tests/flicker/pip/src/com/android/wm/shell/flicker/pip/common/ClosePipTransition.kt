

package com.android.wm.shell.flicker.pip.common

import android.platform.test.annotations.Presubmit
import android.tools.Rotation
import android.tools.flicker.legacy.FlickerBuilder
import android.tools.flicker.legacy.LegacyFlickerTest
import android.tools.flicker.legacy.LegacyFlickerTestFactory
import android.tools.traces.component.ComponentNameMatcher.Companion.LAUNCHER
import com.android.server.wm.flicker.helpers.setRotation
import org.junit.Test
import org.junit.runners.Parameterized

/** Base class for exiting pip (closing pip window) without returning to the app */
abstract class ClosePipTransition(flicker: LegacyFlickerTest) : PipTransition(flicker) {
    override val thisTransition: FlickerBuilder.() -> Unit = {
        setup { this.setRotation(flicker.scenario.startRotation) }
        teardown { this.setRotation(Rotation.ROTATION_0) }
    }

    /**
     * Checks that [pipApp] window is pinned and visible at the start and then becomes unpinned and
     * invisible at the same moment, and remains unpinned and invisible until the end of the
     * transition
     */
    @Presubmit
    @Test
    open fun pipWindowBecomesInvisible() {
        // When Shell transition is enabled, we change the windowing mode at start, but
        // update the visibility after the transition is finished, so we can't check isNotPinned
        // and isAppWindowInvisible in the same assertion block.
        flicker.assertWm {
            this.invoke("hasPipWindow") {
                    it.isPinned(pipApp).isAppWindowVisible(pipApp).isAppWindowOnTop(pipApp)
                }
                .then()
                .invoke("!hasPipWindow") { it.isNotPinned(pipApp).isAppWindowNotOnTop(pipApp) }
        }
        flicker.assertWmEnd { isAppWindowInvisible(pipApp) }
    }

    /**
     * Checks that [pipApp] and [LAUNCHER] layers are visible at the start of the transition. Then
     * [pipApp] layer becomes invisible, and remains invisible until the end of the transition
     */
    @Presubmit
    @Test
    open fun pipLayerBecomesInvisible() {
        flicker.assertLayers {
            this.isVisible(pipApp)
                .isVisible(LAUNCHER)
                .then()
                .isInvisible(pipApp)
                .isVisible(LAUNCHER)
        }
    }

    companion object {
        /**
         * Creates the test configurations.
         *
         * See [LegacyFlickerTestFactory.nonRotationTests] for configuring repetitions, screen
         * orientation and navigation modes.
         */
        @Parameterized.Parameters(name = "{0}")
        @JvmStatic
        fun getParams() =
            LegacyFlickerTestFactory.nonRotationTests(
                supportedRotations = listOf(Rotation.ROTATION_0)
            )
    }
}
