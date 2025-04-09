
package com.android.launcher3.tapl;

import androidx.test.uiautomator.UiObject2;

import com.android.launcher3.testing.shared.TestProtocol;

import java.util.regex.Pattern;

/**
 * Operations on a search web suggestion from a qsb.
 */
public class SearchWebSuggestion extends Launchable {

    private static final Pattern LONG_CLICK_EVENT = Pattern.compile("onAllAppsItemLongClick");

    SearchWebSuggestion(LauncherInstrumentation launcher, UiObject2 object) {
        super(launcher, object);
    }

    @Override
    protected void expectActivityStartEvents() {
    }

    @Override
    protected String launchableType() {
        return "search web suggestion";
    }

    @Override
    protected void waitForLongPressConfirmation() {
        mLauncher.waitForLauncherObject("popup_container");
    }

    @Override
    protected void addExpectedEventsForLongClick() {
        mLauncher.expectEvent(TestProtocol.SEQUENCE_MAIN, getLongClickEvent());
    }

    protected Pattern getLongClickEvent() {
        return LONG_CLICK_EVENT;
    }
}
