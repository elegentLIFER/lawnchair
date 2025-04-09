

package com.android.launcher3.tapl;

import androidx.test.uiautomator.UiObject2;

import java.util.regex.Pattern;

/**
 * App icon in all apps.
 */
final class AllAppsAppIcon extends HomeAppIcon {

    private static final Pattern LONG_CLICK_EVENT = Pattern.compile("onAllAppsItemLongClick");

    AllAppsAppIcon(LauncherInstrumentation launcher, UiObject2 icon) {
        super(launcher, icon);
    }

    @Override
    protected Pattern getLongClickEvent() {
        return LONG_CLICK_EVENT;
    }
}
