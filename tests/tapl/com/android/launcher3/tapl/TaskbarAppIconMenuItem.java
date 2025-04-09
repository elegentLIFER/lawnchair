
package com.android.launcher3.tapl;

import androidx.test.uiautomator.UiObject2;

import com.android.launcher3.testing.shared.TestProtocol;

import java.util.regex.Pattern;

/**
 * Menu item in a Taskbar app icon menu.
 */
public final class TaskbarAppIconMenuItem extends AppIconMenuItem implements SplitscreenDragSource {

    private static final Pattern LONG_CLICK_EVENT = Pattern.compile("onTaskbarItemLongClick");

    TaskbarAppIconMenuItem(
            LauncherInstrumentation launcher, UiObject2 shortcut) {
        super(launcher, shortcut);
    }

    @Override
    protected void addExpectedEventsForLongClick() {
        mLauncher.expectEvent(TestProtocol.SEQUENCE_MAIN, LONG_CLICK_EVENT);
    }

    @Override
    protected void waitForLongPressConfirmation() {
        // On long-press, the popup container closes and the system drag-and-drop begins. This
        // only leaves launcher views that were previously visible.
        mLauncher.waitUntilLauncherObjectGone("popup_container");
    }

    @Override
    protected String launchableType() {
        return "taskbar app icon menu item";
    }

    @Override
    public Launchable getLaunchable() {
        return this;
    }

    @Override
    protected boolean launcherStopsAfterLaunch() {
        return false;
    }
}
