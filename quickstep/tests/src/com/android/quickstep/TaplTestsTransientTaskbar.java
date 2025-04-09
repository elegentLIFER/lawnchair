
package com.android.quickstep;

import static com.android.launcher3.Flags.enableCursorHoverStates;
import static com.android.launcher3.util.TestConstants.AppNames.TEST_APP_NAME;
import static com.android.quickstep.TaskbarModeSwitchRule.Mode.TRANSIENT;

import static org.junit.Assume.assumeTrue;

import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import com.android.quickstep.TaskbarModeSwitchRule.TaskbarModeSwitch;

import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TaplTestsTransientTaskbar extends AbstractTaplTestsTaskbar {

    @Test
    @TaskbarModeSwitch(mode = TRANSIENT)
    public void testShowTaskbarUnstashHintOnHover() {
        assumeTrue(enableCursorHoverStates());
        getTaskbar().getAppIcon(TEST_APP_NAME).launch(TEST_APP_PACKAGE);
        mLauncher.getLaunchedAppState().hoverToShowTaskbarUnstashHint();
    }

    @Test
    @TaskbarModeSwitch(mode = TRANSIENT)
    public void testUnstashTaskbarOnScreenBottomEdgeHover() {
        assumeTrue(enableCursorHoverStates());
        getTaskbar().getAppIcon(TEST_APP_NAME).launch(TEST_APP_PACKAGE);
        mLauncher.getLaunchedAppState().hoverScreenBottomEdgeToUnstashTaskbar();
    }

    @Test
    @TaskbarModeSwitch(mode = TRANSIENT)
    public void testHoverBelowHintedTaskbarToUnstash() {
        assumeTrue(enableCursorHoverStates());
        getTaskbar().getAppIcon(TEST_APP_NAME).launch(TEST_APP_PACKAGE);
        mLauncher.getLaunchedAppState().hoverBelowHintedTaskbarToUnstash();
    }

    @Test
    @TaskbarModeSwitch(mode = TRANSIENT)
    public void testClickHoveredTaskbarToGoHome() throws Exception {
        assumeTrue(enableCursorHoverStates());
        getTaskbar().getAppIcon(TEST_APP_NAME).launch(TEST_APP_PACKAGE);
        mLauncher.getLaunchedAppState().clickStashedTaskbarToGoHome();
    }

    @Test
    @TaskbarModeSwitch(mode = TRANSIENT)
    public void testSwipeToStashAndUnstash() {
        getTaskbar().swipeDownToStash();
        mLauncher.getLaunchedAppState().swipeUpToUnstashTaskbar();
    }
}
