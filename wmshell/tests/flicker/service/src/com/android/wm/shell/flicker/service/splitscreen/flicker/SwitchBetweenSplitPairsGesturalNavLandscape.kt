

package com.android.wm.shell.flicker.service.splitscreen.flicker

import android.tools.Rotation
import android.tools.flicker.FlickerConfig
import android.tools.flicker.annotation.ExpectedScenarios
import android.tools.flicker.annotation.FlickerConfigProvider
import android.tools.flicker.config.FlickerConfig
import android.tools.flicker.config.FlickerServiceConfig
import android.tools.flicker.junit.FlickerServiceJUnit4ClassRunner
import com.android.wm.shell.flicker.service.splitscreen.scenarios.SwitchBetweenSplitPairs
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(FlickerServiceJUnit4ClassRunner::class)
class SwitchBetweenSplitPairsGesturalNavLandscape : SwitchBetweenSplitPairs(Rotation.ROTATION_90) {

    @ExpectedScenarios(["QUICKSWITCH"])
    @Test
    override fun switchBetweenSplitPairs() = super.switchBetweenSplitPairs()

    companion object {
        @JvmStatic
        @FlickerConfigProvider
        fun flickerConfigProvider(): FlickerConfig =
            FlickerConfig().use(FlickerServiceConfig.DEFAULT)
    }
}
