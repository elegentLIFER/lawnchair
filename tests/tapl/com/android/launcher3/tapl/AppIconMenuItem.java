

package com.android.launcher3.tapl;

import androidx.test.uiautomator.UiObject2;

import com.android.launcher3.testing.shared.TestProtocol;

/**
 * Menu item in an app icon menu.
 */
public abstract class AppIconMenuItem extends Launchable {

    AppIconMenuItem(LauncherInstrumentation launcher, UiObject2 shortcut) {
        super(launcher, shortcut);
    }

    /**
     * Returns the visible text of the menu item.
     */
    public String getText() {
        return mObject.getText();
    }

    @Override
    protected void addExpectedEventsForLongClick() {
    }

    @Override
    protected void waitForLongPressConfirmation() {
        mLauncher.waitForLauncherObject("drop_target_bar");
    }

    @Override
    protected void expectActivityStartEvents() {
        mLauncher.expectEvent(TestProtocol.SEQUENCE_MAIN, LauncherInstrumentation.EVENT_START);
    }

    @Override
    protected String launchableType() {
        return "app icon menu item";
    }
}
