
package com.android.launcher3.tapl;

import androidx.test.uiautomator.UiObject2;

/**
 * Menu item in a home screen app icon menu.
 */
public final class HomeAppIconMenuItem extends AppIconMenuItem implements WorkspaceDragSource {

    HomeAppIconMenuItem(LauncherInstrumentation launcher,
            UiObject2 shortcut) {
        super(launcher, shortcut);
    }

    /** This method requires public access, however should not be called in tests. */
    @Override
    public Launchable getLaunchable() {
        return this;
    }
}
