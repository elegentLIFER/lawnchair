

package com.android.wm.shell.flicker

import android.app.Instrumentation
import android.tools.flicker.legacy.LegacyFlickerTest
import android.tools.traces.component.ComponentNameMatcher
import androidx.test.platform.app.InstrumentationRegistry
import com.android.launcher3.tapl.LauncherInstrumentation
import com.android.wm.shell.flicker.utils.ICommonAssertions

/**
 * Base test class containing common assertions for [ComponentNameMatcher.NAV_BAR],
 * [ComponentNameMatcher.TASK_BAR], [ComponentNameMatcher.STATUS_BAR], and general assertions
 * (layers visible in consecutive states, entire screen covered, etc.)
 */
abstract class BaseTest
@JvmOverloads
constructor(
    override val flicker: LegacyFlickerTest,
    instrumentation: Instrumentation = InstrumentationRegistry.getInstrumentation(),
    tapl: LauncherInstrumentation = LauncherInstrumentation()
) : BaseBenchmarkTest(flicker, instrumentation, tapl), ICommonAssertions
