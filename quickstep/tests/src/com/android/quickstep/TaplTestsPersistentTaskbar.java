
package com.android.quickstep;

import static com.android.quickstep.TaskbarModeSwitchRule.Mode.PERSISTENT;

import android.graphics.Rect;

import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import com.android.launcher3.ui.PortraitLandscapeRunner.PortraitLandscape;
import com.android.quickstep.NavigationModeSwitchRule.NavigationModeSwitch;
import com.android.quickstep.TaskbarModeSwitchRule.TaskbarModeSwitch;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TaplTestsPersistentTaskbar extends AbstractTaplTestsTaskbar {

    @Test
    @TaskbarModeSwitch(mode = PERSISTENT)
    @PortraitLandscape
    @NavigationModeSwitch
    public void testTaskbarFillsWidth() {
        // Width check is performed inside TAPL whenever getTaskbar() is called.
        getTaskbar();
    }

    @Test
    @NavigationModeSwitch(mode = NavigationModeSwitchRule.Mode.THREE_BUTTON)
    public void testThreeButtonsTaskbarBoundsAfterConfigChangeDuringIme() {
        Rect taskbarBoundsBefore = getTaskbar().getVisibleBounds();
        // Go home and to an IME activity (any configuration change would do, as long as it
        // triggers taskbar insets or height change while taskbar is stashed).
        mLauncher.goHome();
        startImeTestActivity();
        // IME should stash the taskbar, which hides icons even in 3 button mode.
        mLauncher.getLaunchedAppState().assertTaskbarHidden();
        // Close IME to check new taskbar bounds.
        startTestActivity(2);
        Rect taskbarBoundsAfter = getTaskbar().getVisibleBounds();
        Assert.assertEquals(
                "Taskbar bounds are not the same after a configuration change while stashed.",
                taskbarBoundsBefore, taskbarBoundsAfter);
    }
}
