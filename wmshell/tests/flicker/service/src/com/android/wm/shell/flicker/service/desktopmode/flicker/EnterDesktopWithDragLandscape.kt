

package com.android.wm.shell.flicker.service.desktopmode.flicker

import android.tools.Rotation
import android.tools.flicker.FlickerConfig
import android.tools.flicker.annotation.ExpectedScenarios
import android.tools.flicker.annotation.FlickerConfigProvider
import android.tools.flicker.config.FlickerConfig
import android.tools.flicker.config.FlickerServiceConfig
import android.tools.flicker.junit.FlickerServiceJUnit4ClassRunner
import com.android.wm.shell.flicker.service.desktopmode.flicker.DesktopModeFlickerScenarios.Companion.END_DRAG_TO_DESKTOP
import com.android.wm.shell.flicker.service.desktopmode.scenarios.EnterDesktopWithDrag
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(FlickerServiceJUnit4ClassRunner::class)
class EnterDesktopWithDragLandscape : EnterDesktopWithDrag(Rotation.ROTATION_90) {
    @ExpectedScenarios(["END_DRAG_TO_DESKTOP"])
    @Test
    override fun enterDesktopWithDrag() = super.enterDesktopWithDrag()

    companion object {

        @JvmStatic
        @FlickerConfigProvider
        fun flickerConfigProvider(): FlickerConfig =
            FlickerConfig().use(FlickerServiceConfig.DEFAULT).use(END_DRAG_TO_DESKTOP)
    }
}
