

package com.android.wm.shell.flicker.service.desktopmode.flicker

import android.tools.Rotation
import android.tools.flicker.FlickerConfig
import android.tools.flicker.annotation.ExpectedScenarios
import android.tools.flicker.annotation.FlickerConfigProvider
import android.tools.flicker.config.FlickerConfig
import android.tools.flicker.config.FlickerServiceConfig
import android.tools.flicker.junit.FlickerServiceJUnit4ClassRunner
import com.android.wm.shell.flicker.service.desktopmode.flicker.DesktopModeFlickerScenarios.Companion.CORNER_RESIZE
import com.android.wm.shell.flicker.service.desktopmode.scenarios.ResizeAppWithCornerResize
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(FlickerServiceJUnit4ClassRunner::class)
class ResizeAppWithCornerResizeLandscape : ResizeAppWithCornerResize(Rotation.ROTATION_90) {
    @ExpectedScenarios(["CORNER_RESIZE"])
    @Test
    override fun resizeAppWithCornerResize() = super.resizeAppWithCornerResize()

    companion object {
        @JvmStatic
        @FlickerConfigProvider
        fun flickerConfigProvider(): FlickerConfig =
            FlickerConfig().use(FlickerServiceConfig.DEFAULT).use(CORNER_RESIZE)
    }
}
