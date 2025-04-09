

package com.android.wm.shell.flicker.service.splitscreen.platinum

import android.platform.test.annotations.PlatinumTest
import android.platform.test.annotations.Presubmit
import com.android.wm.shell.flicker.service.splitscreen.scenarios.UnlockKeyguardToSplitScreen
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner

@RunWith(BlockJUnit4ClassRunner::class)
open class UnlockKeyguardToSplitScreenGesturalNavLandscape : UnlockKeyguardToSplitScreen() {
    @PlatinumTest(focusArea = "sysui")
    @Presubmit
    @Test
    override fun unlockKeyguardToSplitScreen() = super.unlockKeyguardToSplitScreen()
}
