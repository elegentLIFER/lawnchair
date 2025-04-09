

package com.android.launcher3.tapl;

import androidx.annotation.NonNull;
import androidx.test.uiautomator.UiObject2;

/**
 * View containing overview actions
 */
public class OverviewActions {
    private final UiObject2 mOverviewActions;
    private final LauncherInstrumentation mLauncher;

    OverviewActions(UiObject2 overviewActions, LauncherInstrumentation launcherInstrumentation) {
        this.mOverviewActions = overviewActions;
        this.mLauncher = launcherInstrumentation;
    }

    /**
     * Clicks screenshot button and closes screenshot ui.
     */
    @NonNull
    public Overview clickAndDismissScreenshot() {
        try (LauncherInstrumentation.Closable e = mLauncher.eventsCheck();
             LauncherInstrumentation.Closable c = mLauncher.addContextLayer(
                     "want to click screenshot button and exit screenshot ui")) {
            mLauncher.setIndefiniteAccessibilityInteractiveUiTimeout(true);

            UiObject2 screenshot = mLauncher.waitForObjectInContainer(mOverviewActions,
                    "action_screenshot");

            mLauncher.clickLauncherObject(screenshot);
            try (LauncherInstrumentation.Closable c1 = mLauncher.addContextLayer(
                    "clicked screenshot button")) {
                UiObject2 closeScreenshot = mLauncher.waitForSystemUiObject(
                        "screenshot_dismiss_image");
                closeScreenshot.click();
                try (LauncherInstrumentation.Closable c2 = mLauncher.addContextLayer(
                        "dismissed screenshot")) {
                    return new Overview(mLauncher);
                }
            }
        } finally {
            mLauncher.setIndefiniteAccessibilityInteractiveUiTimeout(false);
        }
    }

    /**
     * Click select button
     *
     * @return The select mode buttons that are now shown instead of action buttons.
     */
    @NonNull
    public SelectModeButtons clickSelect() {
        try (LauncherInstrumentation.Closable e = mLauncher.eventsCheck();
             LauncherInstrumentation.Closable c =
                     mLauncher.addContextLayer("want to click select button")) {
            UiObject2 select = mLauncher.waitForObjectInContainer(mOverviewActions,
                    "action_select");
            mLauncher.clickLauncherObject(select);
            try (LauncherInstrumentation.Closable c1 = mLauncher.addContextLayer(
                    "clicked select button")) {
                return getSelectModeButtons();
            }
        }
    }

    /**
     * Gets the Select Mode Buttons.
     *
     * @return The Select Mode Buttons.
     */
    @NonNull
    private SelectModeButtons getSelectModeButtons() {
        try (LauncherInstrumentation.Closable c = mLauncher.addContextLayer(
                "want to get select mode buttons")) {
            return new SelectModeButtons(mLauncher);
        }
    }

    /**
     * Clicks split button and enters split select mode.
     */
    @NonNull
    public SplitScreenSelect clickSplit() {
        try (LauncherInstrumentation.Closable e = mLauncher.eventsCheck();
             LauncherInstrumentation.Closable c = mLauncher.addContextLayer(
                     "want to click split button to enter split select mode")) {
            UiObject2 split = mLauncher.waitForObjectInContainer(mOverviewActions,
                    "action_split");
            mLauncher.clickLauncherObject(split);
            try (LauncherInstrumentation.Closable c2 = mLauncher.addContextLayer(
                    "clicked split")) {
                return new SplitScreenSelect(mLauncher);
            }
        }
    }
}
