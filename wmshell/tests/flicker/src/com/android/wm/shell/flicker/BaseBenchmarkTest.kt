

package com.android.wm.shell.flicker

import android.app.Instrumentation
import android.tools.flicker.junit.FlickerBuilderProvider
import android.tools.flicker.legacy.FlickerBuilder
import android.tools.flicker.legacy.LegacyFlickerTest
import androidx.test.platform.app.InstrumentationRegistry
import com.android.launcher3.tapl.LauncherInstrumentation

abstract class BaseBenchmarkTest
@JvmOverloads
constructor(
    protected open val flicker: LegacyFlickerTest,
    protected val instrumentation: Instrumentation = InstrumentationRegistry.getInstrumentation(),
    protected val tapl: LauncherInstrumentation = LauncherInstrumentation()
) {
    /** Specification of the test transition to execute */
    abstract val transition: FlickerBuilder.() -> Unit

    /**
     * Entry point for the test runner. It will use this method to initialize and cache flicker
     * executions
     */
    @FlickerBuilderProvider
    open fun buildFlicker(): FlickerBuilder {
        return FlickerBuilder(instrumentation).apply {
            setup { flicker.scenario.setIsTablet(tapl.isTablet) }
            transition()
        }
    }
}
