

package com.android.wm.shell.desktopmode;

/**
 * Allows external processes to register a listener in WMShell to get updates about desktop task
 * state.
 */
interface IDesktopTaskListener {

    /** Desktop tasks visibility has changed. Visible if at least 1 task is visible. */
    oneway void onTasksVisibilityChanged(int displayId, int visibleTasksCount);

    /** @deprecated this is no longer supported. */
    oneway void onStashedChanged(int displayId, boolean stashed);
}
