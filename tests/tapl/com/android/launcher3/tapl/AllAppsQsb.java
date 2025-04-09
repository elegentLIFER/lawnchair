
package com.android.launcher3.tapl;

import androidx.test.uiautomator.UiObject2;

/**
 * Operations on AllApp screen qsb.
 */
class AllAppsQsb extends Qsb {

    AllAppsQsb(LauncherInstrumentation launcher, UiObject2 allAppsContainer) {
        super(launcher, allAppsContainer, "search_container_all_apps");
    }
}
