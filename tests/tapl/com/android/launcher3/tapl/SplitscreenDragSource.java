
package com.android.launcher3.tapl;

/** {@link Launchable} that can serve as a source for dragging and dropping to splitscreen. */
interface SplitscreenDragSource {

    /**
     * Drags this app icon to the left (landscape) or bottom (portrait) of the screen, launching it
     * in splitscreen.
     *
     * @param expectedNewPackageName package name of the app being dragged
     * @param expectedExistingPackageName package name of the already-launched app
     */
    default void dragToSplitscreen(
            String expectedNewPackageName, String expectedExistingPackageName) {
        Launchable launchable = getLaunchable();
        LauncherInstrumentation launcher = launchable.mLauncher;
        try (LauncherInstrumentation.Closable e = launcher.eventsCheck()) {
            LaunchedAppState.dragToSplitscreen(
                    launcher, launchable, expectedNewPackageName, expectedExistingPackageName);
        }
    }

    /** This method requires public access, however should not be called in tests. */
    Launchable getLaunchable();
}
