
package com.android.launcher3.tapl;

import androidx.test.uiautomator.UiObject2;

/**
 * Context menu of a home screen app icon.
 */
public final class HomeAppIconMenu extends AppIconMenu {

    HomeAppIconMenu(LauncherInstrumentation launcher,
            UiObject2 deepShortcutsContainer) {
        super(launcher, deepShortcutsContainer);
    }

    @Override
    public HomeAppIconMenuItem getMenuItem(int itemNumber) {
        return (HomeAppIconMenuItem) super.getMenuItem(itemNumber);
    }

    @Override
    protected HomeAppIconMenuItem createMenuItem(UiObject2 menuItem) {
        return new HomeAppIconMenuItem(mLauncher, menuItem);
    }
}
