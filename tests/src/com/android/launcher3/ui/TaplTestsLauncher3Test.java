

package com.android.launcher3.ui;

import static org.junit.Assert.assertNotNull;

import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import com.android.launcher3.util.rule.ScreenRecordRule.ScreenRecord;
import com.android.launcher3.Launcher;

import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TaplTestsLauncher3Test extends AbstractLauncherUiTest<Launcher> {

    @ScreenRecord // b/322823478
    @Test
    public void testDevicePressMenu() throws Exception {
        mDevice.pressMenu();
        mDevice.waitForIdle();
        executeOnLauncher(
                launcher -> assertNotNull("Launcher internal state didn't switch to Showing Menu",
                        launcher.getOptionsPopup()));
        // Check that pressHome works when the menu is shown.
        mLauncher.goHome();
    }
}
