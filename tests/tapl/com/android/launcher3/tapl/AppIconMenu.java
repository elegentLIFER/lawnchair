

package com.android.launcher3.tapl;

import static org.junit.Assert.assertTrue;

import androidx.test.uiautomator.UiObject2;

import java.util.List;

/**
 * Context menu of an app icon.
 */
public abstract class AppIconMenu {
    protected final LauncherInstrumentation mLauncher;
    protected final UiObject2 mDeepShortcutsContainer;

    AppIconMenu(LauncherInstrumentation launcher,
            UiObject2 deepShortcutsContainer) {
        mLauncher = launcher;
        mDeepShortcutsContainer = deepShortcutsContainer;
    }

    /**
     * Returns a menu item with a given number. Fails if it doesn't exist.
     */
    public AppIconMenuItem getMenuItem(int itemNumber) {
        final List<UiObject2> menuItems = mLauncher.getObjectsInContainer(mDeepShortcutsContainer,
                "bubble_text");
        assertTrue(menuItems.size() > itemNumber);
        return createMenuItem(menuItems.get(itemNumber));
    }

    /**
     * Returns a menu item with the given text. Fails if it doesn't exist.
     */
    public AppIconMenuItem getMenuItem(String shortcutText) {
        final UiObject2 menuItem = mLauncher.waitForObjectInContainer(mDeepShortcutsContainer,
                AppIcon.getMenuItemSelector(shortcutText, mLauncher));
        return createMenuItem(menuItem);
    }

    /**
     * Returns a menu item that matches the text "Split screen". Fails if it doesn't exist.
     */
    public SplitScreenMenuItem getSplitScreenMenuItem() {
        final UiObject2 menuItem = mLauncher.waitForObjectInContainer(mDeepShortcutsContainer,
                AppIcon.getMenuItemSelector("Split screen", mLauncher));
        return new SplitScreenMenuItem(mLauncher, menuItem);
    }

    protected abstract AppIconMenuItem createMenuItem(UiObject2 menuItem);
}
