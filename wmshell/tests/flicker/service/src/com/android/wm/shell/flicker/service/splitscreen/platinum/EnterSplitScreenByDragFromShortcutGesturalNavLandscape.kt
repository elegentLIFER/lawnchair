

package com.android.wm.shell.flicker.service.splitscreen.platinum

import android.platform.test.annotations.PlatinumTest
import android.platform.test.annotations.Presubmit
import android.tools.Rotation
import com.android.wm.shell.flicker.service.splitscreen.scenarios.EnterSplitScreenByDragFromShortcut
import org.junit.Test

open class EnterSplitScreenByDragFromShortcutGesturalNavLandscape :
    EnterSplitScreenByDragFromShortcut(Rotation.ROTATION_90) {
    @PlatinumTest(focusArea = "sysui")
    @Presubmit
    @Test
    override fun enterSplitScreenByDragFromShortcut() = super.enterSplitScreenByDragFromShortcut()
}
