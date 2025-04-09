

package com.android.wm.shell.flicker.service.splitscreen.platinum

import android.platform.test.annotations.PlatinumTest
import android.platform.test.annotations.Presubmit
import android.tools.Rotation
import com.android.wm.shell.flicker.service.splitscreen.scenarios.SwitchBetweenSplitPairs
import org.junit.Test

open class SwitchBetweenSplitPairsGesturalNavLandscape :
    SwitchBetweenSplitPairs(Rotation.ROTATION_90) {
    @PlatinumTest(focusArea = "sysui")
    @Presubmit
    @Test
    override fun switchBetweenSplitPairs() = super.switchBetweenSplitPairs()
}
