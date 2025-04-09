

package com.android.launcher3.tapl;

import android.graphics.Rect;

import androidx.annotation.NonNull;
import androidx.test.uiautomator.UiObject2;

public class Folder {

    protected static final String FOLDER_CONTENT_RES_ID = "folder_content";

    private final UiObject2 mContainer;
    private final LauncherInstrumentation mLauncher;

    Folder(LauncherInstrumentation launcher) {
        this.mLauncher = launcher;
        this.mContainer = launcher.waitForLauncherObject(FOLDER_CONTENT_RES_ID);
    }

    /**
     * Find an app icon with given name or raise assertion error.
     */
    @NonNull
    public HomeAppIcon getAppIcon(String appName) {
        try (LauncherInstrumentation.Closable ignored = mLauncher.addContextLayer(
                "Want to get app icon in folder")) {
            return new WorkspaceAppIcon(mLauncher,
                    mLauncher.waitForObjectInContainer(
                            mContainer,
                            AppIcon.getAppIconSelector(appName, mLauncher)));
        }
    }

    /**
     * Close opened folder if possible. It throws assertion error if the folder is already closed.
     */
    public Workspace close() {
        try (LauncherInstrumentation.Closable e = mLauncher.eventsCheck();
             LauncherInstrumentation.Closable c = mLauncher.addContextLayer(
                     "Want to close opened folder")) {
            mLauncher.waitForLauncherObject(FOLDER_CONTENT_RES_ID);
            mLauncher.touchOutsideContainer(this.mContainer, false /* tapRight */);
            mLauncher.waitUntilLauncherObjectGone(FOLDER_CONTENT_RES_ID);
            return mLauncher.getWorkspace();
        }
    }
    Rect getDropLocationBounds() {
        return mLauncher.getVisibleBounds(mContainer);
    }
}
