

package com.android.wm.shell.flicker.service.splitscreen.platinum

import android.platform.test.annotations.PlatinumTest
import android.platform.test.annotations.Presubmit
import android.tools.Rotation
import com.android.wm.shell.flicker.service.splitscreen.scenarios.SwitchAppByDoubleTapDivider
import org.junit.Test

open class SwitchAppByDoubleTapDividerGesturalNavPortrait :
    SwitchAppByDoubleTapDivider(Rotation.ROTATION_0) {
    @PlatinumTest(focusArea = "sysui")
    @Presubmit
    @Test
    override fun switchAppByDoubleTapDivider() = super.switchAppByDoubleTapDivider()
}
