

package com.android.wm.shell.flicker.service.desktopmode.flicker

import android.tools.Rotation
import android.tools.flicker.FlickerConfig
import android.tools.flicker.annotation.ExpectedScenarios
import android.tools.flicker.annotation.FlickerConfigProvider
import android.tools.flicker.config.FlickerConfig
import android.tools.flicker.config.FlickerServiceConfig
import android.tools.flicker.junit.FlickerServiceJUnit4ClassRunner
import com.android.wm.shell.flicker.service.desktopmode.flicker.DesktopModeFlickerScenarios.Companion.CLOSE_APP
import com.android.wm.shell.flicker.service.desktopmode.flicker.DesktopModeFlickerScenarios.Companion.CLOSE_LAST_APP
import com.android.wm.shell.flicker.service.desktopmode.scenarios.CloseAllAppsWithAppHeaderExit
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(FlickerServiceJUnit4ClassRunner::class)
class CloseAllAppWithAppHeaderExitLandscape : CloseAllAppsWithAppHeaderExit(Rotation.ROTATION_90) {
    @ExpectedScenarios(["CLOSE_APP", "CLOSE_LAST_APP"])
    @Test
    override fun closeAllAppsInDesktop() = super.closeAllAppsInDesktop()

    companion object {
        @JvmStatic
        @FlickerConfigProvider
        fun flickerConfigProvider(): FlickerConfig =
            FlickerConfig()
                .use(FlickerServiceConfig.DEFAULT)
                .use(CLOSE_APP)
                .use(CLOSE_LAST_APP)
    }
}
