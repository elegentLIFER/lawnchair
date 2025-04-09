

package com.android.quickstep.util;

/**
 * Constants for registering SystemActions.
 *
 * Prefer to use AccessibilityService.GLOBAL_ACTION_* if applicable.
 */
public final class SystemActionConstants {

    public static final int SYSTEM_ACTION_ID_TASKBAR = 499;
    public static final int SYSTEM_ACTION_ID_SEARCH_SCREEN = 500;

    /**
     * For Taskbar broadcast intent filter.
     */
    public static final String ACTION_SHOW_TASKBAR = "ACTION_SHOW_TASKBAR";

    /**
     * For Search Screen broadcast intent filter.
     */
    public static final String ACTION_SEARCH_SCREEN = "ACTION_SEARCH_SCREEN";

    private SystemActionConstants() {}
}
