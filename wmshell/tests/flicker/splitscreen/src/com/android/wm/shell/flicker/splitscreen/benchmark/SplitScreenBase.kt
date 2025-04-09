

package com.android.wm.shell.flicker.splitscreen.benchmark

import android.content.Context
import android.tools.flicker.legacy.FlickerBuilder
import android.tools.flicker.legacy.LegacyFlickerTest
import com.android.server.wm.flicker.helpers.setRotation
import com.android.wm.shell.flicker.BaseBenchmarkTest
import com.android.wm.shell.flicker.utils.SplitScreenUtils

abstract class SplitScreenBase(flicker: LegacyFlickerTest) : BaseBenchmarkTest(flicker) {
    protected val context: Context = instrumentation.context
    protected open val primaryApp = SplitScreenUtils.getPrimary(instrumentation)
    protected val secondaryApp = SplitScreenUtils.getSecondary(instrumentation)

    protected open val defaultSetup: FlickerBuilder.() -> Unit = {
        setup {
            tapl.setEnableRotation(true)
            setRotation(flicker.scenario.startRotation)
            tapl.setExpectedRotation(flicker.scenario.startRotation.value)
            val overview = tapl.workspace.switchToOverview()
            if (overview.hasTasks()) {
                overview.dismissAllTasks()
            }
        }
    }

    protected open val defaultTeardown: FlickerBuilder.() -> Unit = {
        teardown {
            primaryApp.exit(wmHelper)
            secondaryApp.exit(wmHelper)
        }
    }
}
