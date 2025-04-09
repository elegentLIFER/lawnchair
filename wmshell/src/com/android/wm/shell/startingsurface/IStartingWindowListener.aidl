

package com.android.wm.shell.startingsurface;

/**
 * Listener interface that Launcher attaches to SystemUI to get
 * callbacks when need a new starting window.
 */
interface IStartingWindowListener {
    /**
     * Notifies when Shell going to create a new starting window.
     * @param taskId The task Id
     * @param supportedType The starting window type
     * @param splashScreenBackgroundColor The splash screen's background color
     */
    oneway void onTaskLaunching(int taskId, int supportedType, int splashScreenBackgroundColor);
}
