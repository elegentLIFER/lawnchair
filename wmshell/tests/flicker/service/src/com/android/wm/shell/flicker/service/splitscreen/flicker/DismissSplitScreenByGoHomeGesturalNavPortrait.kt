

package com.android.wm.shell.flicker.service.splitscreen.flicker

import android.tools.Rotation
import android.tools.flicker.FlickerConfig
import android.tools.flicker.annotation.ExpectedScenarios
import android.tools.flicker.annotation.FlickerConfigProvider
import android.tools.flicker.config.FlickerConfig
import android.tools.flicker.config.FlickerServiceConfig
import android.tools.flicker.junit.FlickerServiceJUnit4ClassRunner
import com.android.wm.shell.flicker.service.splitscreen.scenarios.DismissSplitScreenByGoHome
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(FlickerServiceJUnit4ClassRunner::class)
class DismissSplitScreenByGoHomeGesturalNavPortrait :
    DismissSplitScreenByGoHome(Rotation.ROTATION_0) {

    @ExpectedScenarios(["APP_CLOSE_TO_HOME"]) // SPLIT_SCREEN_EXIT not yet tagged here (b/301222449)
    @Test
    override fun dismissSplitScreenByGoHome() = super.dismissSplitScreenByGoHome()

    companion object {
        @JvmStatic
        @FlickerConfigProvider
        fun flickerConfigProvider(): FlickerConfig =
            FlickerConfig().use(FlickerServiceConfig.DEFAULT)
    }
}
