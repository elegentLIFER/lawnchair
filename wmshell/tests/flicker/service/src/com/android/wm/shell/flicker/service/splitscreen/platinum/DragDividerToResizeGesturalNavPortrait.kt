

package com.android.wm.shell.flicker.service.splitscreen.platinum

import android.platform.test.annotations.PlatinumTest
import android.platform.test.annotations.Presubmit
import android.tools.Rotation
import com.android.wm.shell.flicker.service.splitscreen.scenarios.DragDividerToResize
import org.junit.Test

open class DragDividerToResizeGesturalNavPortrait : DragDividerToResize(Rotation.ROTATION_0) {
    @PlatinumTest(focusArea = "sysui")
    @Presubmit
    @Test
    override fun dragDividerToResize() = super.dragDividerToResize()
}
