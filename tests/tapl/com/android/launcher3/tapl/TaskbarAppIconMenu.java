
package com.android.launcher3.tapl;

import androidx.test.uiautomator.UiObject2;

/**
 * Context menu of a Taskbar app icon.
 */
public final class TaskbarAppIconMenu extends AppIconMenu {

    TaskbarAppIconMenu(LauncherInstrumentation launcher, UiObject2 deepShortcutsContainer) {
        super(launcher, deepShortcutsContainer);
    }

    @Override
    public TaskbarAppIconMenuItem getMenuItem(String shortcutText) {
        return (TaskbarAppIconMenuItem) super.getMenuItem(shortcutText);
    }

    @Override
    protected TaskbarAppIconMenuItem createMenuItem(UiObject2 menuItem) {
        return new TaskbarAppIconMenuItem(mLauncher, menuItem);
    }
}
