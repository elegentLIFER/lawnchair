

package com.android.launcher3.tapl;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.BySelector;
import androidx.test.uiautomator.UiObject2;

import java.util.regex.Pattern;

public class AddToHomeScreenPrompt {
    private static final Pattern ADD_AUTOMATICALLY =
            Pattern.compile("^Add to Home screen$", CASE_INSENSITIVE);
    private final LauncherInstrumentation mLauncher;
    private final UiObject2 mWidgetCell;

    AddToHomeScreenPrompt(LauncherInstrumentation launcher) {
        mLauncher = launcher;
        mWidgetCell = launcher.waitForLauncherObject(getSelector());
        mLauncher.assertNotNull("Can't find widget cell object", mWidgetCell);
    }

    private static BySelector getSelector() {
        return By.clazz("com.android.launcher3.widget.WidgetCell");
    }

    public void addAutomatically() {
        try (LauncherInstrumentation.Closable e = mLauncher.eventsCheck()) {
            mLauncher.clickObject(
                    mLauncher.waitForObjectInContainer(
                            mWidgetCell.getParent().getParent().getParent().getParent(),
                            By.text(ADD_AUTOMATICALLY))
            );
            mLauncher.waitUntilLauncherObjectGone(getSelector());
        }
    }
}
