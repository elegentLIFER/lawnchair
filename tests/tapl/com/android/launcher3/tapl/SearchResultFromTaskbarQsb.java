
package com.android.launcher3.tapl;

import androidx.test.uiautomator.UiObject2;

/**
 * Operations on search result page opened from Taskbar qsb.
 */
public class SearchResultFromTaskbarQsb extends SearchResultFromQsb {

    SearchResultFromTaskbarQsb(LauncherInstrumentation launcher) {
        super(launcher);
    }

    @Override
    public TaskbarAppIcon findAppIcon(String appName) {
        return (TaskbarAppIcon) super.findAppIcon(appName);
    }

    @Override
    protected TaskbarAppIcon createAppIcon(UiObject2 icon) {
        return new TaskbarAppIcon(mLauncher, icon);
    }

    @Override
    public TaskbarSearchWebSuggestion findWebSuggestion(String text) {
        return (TaskbarSearchWebSuggestion) super.findWebSuggestion(text);
    }

    @Override
    protected TaskbarSearchWebSuggestion createWebSuggestion(UiObject2 webSuggestion) {
        return new TaskbarSearchWebSuggestion(mLauncher, webSuggestion);
    }

    @Override
    protected void verifyVisibleContainerOnDismiss() {
        mLauncher.getLaunchedAppState().assertTaskbarVisible();
    }

    @Override
    protected void tapOutside(boolean tapRight, UiObject2 allAppsBottomSheet) {
        mLauncher.touchOutsideContainer(allAppsBottomSheet, tapRight);
    }
}
