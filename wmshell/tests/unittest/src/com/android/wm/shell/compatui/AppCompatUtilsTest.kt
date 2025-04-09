

package com.android.wm.shell.compatui

import android.testing.AndroidTestingRunner
import androidx.test.filters.SmallTest
import com.android.wm.shell.ShellTestCase
import com.android.wm.shell.desktopmode.DesktopTestHelpers.Companion.createFreeformTask
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests for {@link AppCompatUtils}.
 *
 * Build/Install/Run:
 *  atest WMShellUnitTests:AppCompatUtilsTest
 */
@RunWith(AndroidTestingRunner::class)
@SmallTest
class AppCompatUtilsTest : ShellTestCase() {

    @Test
    fun testIsSingleTopActivityTranslucent() {
        assertTrue(isSingleTopActivityTranslucent(
            createFreeformTask(/* displayId */ 0)
                    .apply {
                        isTopActivityTransparent = true
                        numActivities = 1
                    }))
        assertFalse(isSingleTopActivityTranslucent(
            createFreeformTask(/* displayId */ 0)
                    .apply {
                        isTopActivityTransparent = true
                        numActivities = 0
                    }))
        assertFalse(isSingleTopActivityTranslucent(
            createFreeformTask(/* displayId */ 0)
                    .apply {
                        isTopActivityTransparent = false
                        numActivities = 1
                    }))
    }
}
