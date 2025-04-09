
package com.android.launcher3.tapl;

import android.graphics.Rect;

import androidx.test.uiautomator.UiObject2;

/** Represents an item in the overview task menu. */
public class OverviewTaskMenuItem {

    private final LauncherInstrumentation mLauncher;
    private final UiObject2 mMenuItem;

    OverviewTaskMenuItem(LauncherInstrumentation launcher, UiObject2 menuItem) {
        mLauncher = launcher;
        mMenuItem = menuItem;
    }

    /**
     * Returns this menu item's visible bounds.
     */
    public Rect getVisibleBounds() {
        return mMenuItem.getVisibleBounds();
    }
}
