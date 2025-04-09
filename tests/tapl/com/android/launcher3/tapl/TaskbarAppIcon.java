
package com.android.launcher3.tapl;

import androidx.test.uiautomator.UiObject2;

import java.util.regex.Pattern;

/**
 * App icon specifically on the Taskbar.
 */
public final class TaskbarAppIcon extends AppIcon implements SplitscreenDragSource {

    private static final Pattern LONG_CLICK_EVENT = Pattern.compile("onTaskbarItemLongClick");
    private static final Pattern RIGHT_CLICK_EVENT = Pattern.compile("onTaskbarItemRightClick");

    TaskbarAppIcon(LauncherInstrumentation launcher, UiObject2 icon) {
        super(launcher, icon);
    }

    @Override
    protected Pattern getLongClickEvent() {
        return LONG_CLICK_EVENT;
    }

    protected Pattern getRightClickEvent() {
        return RIGHT_CLICK_EVENT;
    }

    @Override
    public TaskbarAppIconMenu openDeepShortcutMenu() {
        return (TaskbarAppIconMenu) super.openDeepShortcutMenu();
    }

    /**
     * Right-clicks the icon to open its menu.
     */
    public TaskbarAppIconMenu openDeepShortcutMenuWithRightClick() {
        try (LauncherInstrumentation.Closable e = mLauncher.addContextLayer(
                "want to return the shortcut menu when icon is right-clicked.")) {
            return createMenu(mLauncher.rightClickAndGet(
                    mObject, /* resName= */ "deep_shortcuts_container", getRightClickEvent()));
        }
    }

    @Override
    protected TaskbarAppIconMenu createMenu(UiObject2 menu) {
        return new TaskbarAppIconMenu(mLauncher, menu);
    }

    @Override
    public Launchable getLaunchable() {
        return this;
    }

    @Override
    protected boolean launcherStopsAfterLaunch() {
        // false because if taskbar is showing then launcher is already stopped.
        return false;
    }
}
