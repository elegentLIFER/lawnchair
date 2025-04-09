
package com.android.quickstep;

import static androidx.test.InstrumentationRegistry.getTargetContext;

import static com.android.launcher3.util.TestConstants.AppNames.TEST_APP_NAME;
import static com.android.quickstep.TaplTestsTaskbar.TaskbarMode.PERSISTENT;
import static com.android.quickstep.TaplTestsTaskbar.TaskbarMode.TRANSIENT;

import androidx.test.filters.LargeTest;

import com.android.launcher3.ui.PortraitLandscapeRunner.PortraitLandscape;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@LargeTest
@RunWith(Parameterized.class)
public class TaplTestsTaskbar extends AbstractTaplTestsTaskbar {

    private final TaplTestsTaskbar.TaskbarMode mTaskbarMode;

    public enum TaskbarMode {
        TRANSIENT, PERSISTENT
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {PERSISTENT}, {TRANSIENT}
        });
    }

    public TaplTestsTaskbar(TaskbarMode mode) {
        mTaskbarMode = mode;
    }

    @Override
    public void setUp() throws Exception {
        mTaskbarWasInTransientMode = isTaskbarInTransientMode(getTargetContext());
        setTaskbarMode(mLauncher, isTaskbarTestModeTransient());
        super.setUp();
    }

    @Override
    public void tearDown() throws Exception {
        setTaskbarMode(mLauncher, mTaskbarWasInTransientMode);
        super.tearDown();
    }

    @Test
    public void testLaunchApp() {
        getTaskbar().getAppIcon(TEST_APP_NAME).launch(TEST_APP_PACKAGE);
        // We are using parameterized test runner to share code between different test cases with
        // taskbar variants. But, sometimes we only need to assert things for particular Taskbar
        // variants.
        if (isTaskbarTestModeTransient()) {
            mLauncher.getLaunchedAppState().assertTaskbarHidden();
        }
    }

    @Test
    public void testOpenMenu() {
        getTaskbar().getAppIcon(TEST_APP_NAME).openMenu();
    }

    @Test
    public void testLaunchShortcut() {
        getTaskbar().getAppIcon(TEST_APP_NAME)
                .openDeepShortcutMenu()
                .getMenuItem("Shortcut 1")
                .launch(TEST_APP_PACKAGE);
    }

    @Test
    @PortraitLandscape
    public void testLaunchAppInSplitscreen() {
        getTaskbar().getAppIcon(TEST_APP_NAME).dragToSplitscreen(
                TEST_APP_PACKAGE, CALCULATOR_APP_PACKAGE);
        // We are using parameterized test runner to share code between different test cases with
        // taskbar variants. But, sometimes we only need to assert things for particular Taskbar
        // variants.
        if (isTaskbarTestModeTransient()) {
            mLauncher.getLaunchedAppState().assertTaskbarHidden();
        }
    }

    @Test
    @PortraitLandscape
    public void testLaunchShortcutInSplitscreen() {
        getTaskbar().getAppIcon(TEST_APP_NAME)
                .openDeepShortcutMenu()
                .getMenuItem("Shortcut 1")
                .dragToSplitscreen(TEST_APP_PACKAGE, CALCULATOR_APP_PACKAGE);
    }

    @Test
    public void testLaunchApp_fromTaskbarAllApps() {
        getTaskbar().openAllApps().getAppIcon(TEST_APP_NAME).launch(TEST_APP_PACKAGE);
    }

    @Test
    public void testOpenMenu_fromTaskbarAllApps() {
        getTaskbar().openAllApps().getAppIcon(TEST_APP_NAME).openMenu();
    }

    @Test
    public void testLaunchShortcut_fromTaskbarAllApps() {
        getTaskbar().openAllApps()
                .getAppIcon(TEST_APP_NAME)
                .openDeepShortcutMenu()
                .getMenuItem("Shortcut 1")
                .launch(TEST_APP_PACKAGE);
    }

    @Test
    @PortraitLandscape
    public void testLaunchAppInSplitscreen_fromTaskbarAllApps() {
        getTaskbar().openAllApps()
                .getAppIcon(TEST_APP_NAME)
                .dragToSplitscreen(TEST_APP_PACKAGE, CALCULATOR_APP_PACKAGE);
    }

    @Test
    @PortraitLandscape
    public void testLaunchShortcutInSplitscreen_fromTaskbarAllApps() {
        getTaskbar().openAllApps()
                .getAppIcon(TEST_APP_NAME)
                .openDeepShortcutMenu()
                .getMenuItem("Shortcut 1")
                .dragToSplitscreen(TEST_APP_PACKAGE, CALCULATOR_APP_PACKAGE);
    }

    @Test
    @PortraitLandscape
    public void testDismissAllAppsByTappingOutsideSheet() {
        getTaskbar().openAllApps().dismissByTappingOutsideForTablet(/* tapRight= */ true);
        getTaskbar().openAllApps().dismissByTappingOutsideForTablet(/* tapRight= */ false);
    }

    @Test
    public void testOpenMenuViaRightClick() {
        getTaskbar().getAppIcon(TEST_APP_NAME).openDeepShortcutMenuWithRightClick();
    }

    private boolean isTaskbarTestModeTransient() {
        return TRANSIENT == mTaskbarMode;
    }
}
