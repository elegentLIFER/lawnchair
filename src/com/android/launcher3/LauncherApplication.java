
package com.android.launcher3;

import android.app.Application;

/**
 * Main application class for Launcher
 */
public class LauncherApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MainProcessInitializer.initialize(this);
    }
}
