

package com.android.quickstep;

import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import com.android.quickstep.NavigationModeSwitchRule.NavigationModeSwitch;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TaplStartLauncherViaGestureTests extends AbstractQuickStepTest {

    static final int STRESS_REPEAT_COUNT = 10;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        mLauncher.goHome();
        // Start an activity where the gestures start.
        startTestActivity(2);
    }

    @Test
    @NavigationModeSwitch
    public void testStressPressHome() {
        for (int i = 0; i < STRESS_REPEAT_COUNT; ++i) {
            // Destroy Launcher activity.
            closeLauncherActivity();

            // The test action.
            mLauncher.goHome();
        }
    }

    @Test
    @NavigationModeSwitch
    public void testStressSwipeToOverview() {
        for (int i = 0; i < STRESS_REPEAT_COUNT; ++i) {
            // Destroy Launcher activity.
            closeLauncherActivity();

            // The test action.
            mLauncher.getLaunchedAppState().switchToOverview();
        }
        closeLauncherActivity();
        mLauncher.goHome();
    }
}
