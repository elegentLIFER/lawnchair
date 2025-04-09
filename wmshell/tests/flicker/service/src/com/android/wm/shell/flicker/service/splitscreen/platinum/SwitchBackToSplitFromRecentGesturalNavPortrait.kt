

package com.android.wm.shell.flicker.service.splitscreen.platinum

import android.platform.test.annotations.PlatinumTest
import android.platform.test.annotations.Presubmit
import android.tools.Rotation
import com.android.wm.shell.flicker.service.splitscreen.scenarios.SwitchBackToSplitFromRecent
import org.junit.Test

open class SwitchBackToSplitFromRecentGesturalNavPortrait :
    SwitchBackToSplitFromRecent(Rotation.ROTATION_0) {
    @PlatinumTest(focusArea = "sysui")
    @Presubmit
    @Test
    override fun switchBackToSplitFromRecent() = super.switchBackToSplitFromRecent()
}
