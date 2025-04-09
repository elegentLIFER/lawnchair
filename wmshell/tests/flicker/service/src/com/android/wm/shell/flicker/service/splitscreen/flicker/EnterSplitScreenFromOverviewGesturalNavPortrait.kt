

package com.android.wm.shell.flicker.service.splitscreen.flicker

import android.tools.Rotation
import android.tools.flicker.FlickerConfig
import android.tools.flicker.annotation.ExpectedScenarios
import android.tools.flicker.annotation.FlickerConfigProvider
import android.tools.flicker.config.FlickerConfig
import android.tools.flicker.config.FlickerServiceConfig
import android.tools.flicker.junit.FlickerServiceJUnit4ClassRunner
import com.android.wm.shell.flicker.service.splitscreen.scenarios.EnterSplitScreenFromOverview
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(FlickerServiceJUnit4ClassRunner::class)
class EnterSplitScreenFromOverviewGesturalNavPortrait :
    EnterSplitScreenFromOverview(Rotation.ROTATION_0) {

    @ExpectedScenarios(["SPLIT_SCREEN_ENTER"])
    @Test
    override fun enterSplitScreenFromOverview() = super.enterSplitScreenFromOverview()

    companion object {
        @JvmStatic
        @FlickerConfigProvider
        fun flickerConfigProvider(): FlickerConfig =
            FlickerConfig().use(FlickerServiceConfig.DEFAULT)
    }
}
