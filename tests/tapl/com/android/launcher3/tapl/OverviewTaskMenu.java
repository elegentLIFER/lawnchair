

package com.android.launcher3.tapl;

import static com.android.launcher3.testing.shared.TestProtocol.OVERVIEW_MODAL_TASK_STATE_ORDINAL;
import static com.android.launcher3.testing.shared.TestProtocol.OVERVIEW_SPLIT_SELECT_ORDINAL;

import androidx.annotation.NonNull;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiObject2;

/** Represents the menu of an overview task. */
public class OverviewTaskMenu {

    private final LauncherInstrumentation mLauncher;
    private final UiObject2 mMenu;

    OverviewTaskMenu(LauncherInstrumentation launcher) {
        mLauncher = launcher;

        mMenu = mLauncher.waitForLauncherObject("menu_option_layout");
        mLauncher.assertTrue("The overview task menus is not visible",
                !mMenu.getVisibleBounds().isEmpty());
    }

    /** Taps the split menu item from the overview task menu. */
    @NonNull
    public SplitScreenSelect tapSplitMenuItem() {
        try (LauncherInstrumentation.Closable e = mLauncher.eventsCheck();
             LauncherInstrumentation.Closable c = mLauncher.addContextLayer(
                     "tap split menu item")) {
            mLauncher.runToState(() -> mLauncher.clickLauncherObject(
                            mLauncher.findObjectInContainer(mMenu, By.textStartsWith("Split"))),
                    OVERVIEW_SPLIT_SELECT_ORDINAL,
                    "tapping split menu item"
            );

            try (LauncherInstrumentation.Closable c1 = mLauncher.addContextLayer(
                    "tapped split menu item")) {
                return new SplitScreenSelect(mLauncher);
            }
        }
    }

    /**
     * Taps the app info item from the overview task menu and returns the LaunchedAppState
     * representing the App info settings page.
     */
    @NonNull
    public LaunchedAppState tapAppInfoMenuItem() {
        try (LauncherInstrumentation.Closable e = mLauncher.eventsCheck();
             LauncherInstrumentation.Closable c = mLauncher.addContextLayer(
                     "before tapping the app info menu item")) {
            mLauncher.executeAndWaitForLauncherStop(
                    () -> mLauncher.clickLauncherObject(
                            mLauncher.findObjectInContainer(mMenu, By.text("App info"))),
                    "tapped app info menu item");

            try (LauncherInstrumentation.Closable c1 = mLauncher.addContextLayer(
                    "tapped app info menu item")) {
                mLauncher.waitUntilSystemLauncherObjectGone("overview_panel");
                return new LaunchedAppState(mLauncher);
            }
        }
    }

    /** Taps the select menu item from the overview task menu. */
    @NonNull
    public SelectModeButtons tapSelectMenuItem() {
        try (LauncherInstrumentation.Closable e = mLauncher.eventsCheck();
             LauncherInstrumentation.Closable c = mLauncher.addContextLayer(
                     "before tapping the select menu item")) {

            mLauncher.runToState(
                    () -> mLauncher.clickLauncherObject(
                            mLauncher.findObjectInContainer(mMenu, By.text("Select"))),
                    OVERVIEW_MODAL_TASK_STATE_ORDINAL, "tapping select menu item");

            try (LauncherInstrumentation.Closable c1 = mLauncher.addContextLayer(
                    "select menu item opened")) {
                return new SelectModeButtons(mLauncher);
            }
        }
    }

    /** Returns true if an item matching the given string is present in the menu. */
    public boolean hasMenuItem(String expectedMenuItemText) {
        UiObject2 menuItem = mLauncher.findObjectInContainer(mMenu, By.text(expectedMenuItemText));
        return menuItem != null;
    }

    /**
     * Returns the menu item specified by name if present.
     */
    public OverviewTaskMenuItem getMenuItemByName(String menuItemName) {
        return new OverviewTaskMenuItem(mLauncher,
                mLauncher.waitForObjectInContainer(mMenu, By.text(menuItemName)));
    }

    /**
     * Taps outside task menu to dismiss it.
     */
    public void touchOutsideTaskMenuToDismiss() {
        mLauncher.touchOutsideContainer(mMenu, false);
    }
}
