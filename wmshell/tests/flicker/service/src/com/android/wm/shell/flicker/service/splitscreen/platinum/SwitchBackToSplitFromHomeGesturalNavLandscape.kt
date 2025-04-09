

package com.android.wm.shell.flicker.service.splitscreen.platinum

import android.platform.test.annotations.PlatinumTest
import android.platform.test.annotations.Presubmit
import android.tools.Rotation
import com.android.wm.shell.flicker.service.splitscreen.scenarios.SwitchBackToSplitFromHome
import org.junit.Test

open class SwitchBackToSplitFromHomeGesturalNavLandscape :
    SwitchBackToSplitFromHome(Rotation.ROTATION_90) {
    @PlatinumTest(focusArea = "sysui")
    @Presubmit
    @Test
    override fun switchBackToSplitFromHome() = super.switchBackToSplitFromHome()
}
