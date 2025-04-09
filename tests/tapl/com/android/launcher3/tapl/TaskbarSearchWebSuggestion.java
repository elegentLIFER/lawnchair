
package com.android.launcher3.tapl;

import androidx.test.uiautomator.UiObject2;

import java.util.regex.Pattern;

/**
 * Operations on a search web suggestion from the Taskbar qsb.
 */
public class TaskbarSearchWebSuggestion extends SearchWebSuggestion implements
        SplitscreenDragSource {

    private static final Pattern LONG_CLICK_EVENT = Pattern.compile("onTaskbarItemLongClick");

    TaskbarSearchWebSuggestion(LauncherInstrumentation launcher,
            UiObject2 object) {
        super(launcher, object);
    }

    @Override
    protected Pattern getLongClickEvent() {
        return LONG_CLICK_EVENT;
    }

    /** This method requires public access, however should not be called in tests. */
    @Override
    public Launchable getLaunchable() {
        return this;
    }

    @Override
    protected boolean launcherStopsAfterLaunch() {
        return false;
    }
}
