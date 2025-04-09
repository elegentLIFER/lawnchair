
package com.android.systemui.plugins;

import android.app.Activity;

import com.android.systemui.plugins.annotations.ProvidesInterface;
import com.android.systemui.plugins.shared.LauncherOverlayManager;

/**
 * Implement this interface to add a -1 content on the home screen.
 */
@ProvidesInterface(action = LauncherOverlayPlugin.ACTION, version = LauncherOverlayPlugin.VERSION)
public interface LauncherOverlayPlugin extends Plugin {
    String ACTION = "com.android.systemui.action.PLUGIN_LAUNCHER_OVERLAY";
    int VERSION = 1;

    LauncherOverlayManager createOverlayManager(Activity activity);

}
