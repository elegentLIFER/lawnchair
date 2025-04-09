
package com.android.launcher3.tapl;

import androidx.test.uiautomator.UiObject2;

/**
 * Operations on Taskbar AllApp screen qsb.
 */
public class TaskbarAllAppsQsb extends Qsb {

    TaskbarAllAppsQsb(LauncherInstrumentation launcher, UiObject2 allAppsContainer) {
        super(launcher, allAppsContainer, "search_container_all_apps");
    }

    @Override
    public SearchResultFromTaskbarQsb showSearchResult() {
        return (SearchResultFromTaskbarQsb) super.showSearchResult();
    }

    @Override
    protected SearchResultFromTaskbarQsb createSearchResult() {
        return new SearchResultFromTaskbarQsb(mLauncher);
    }
}
