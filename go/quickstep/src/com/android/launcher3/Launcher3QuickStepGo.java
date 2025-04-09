

package com.android.launcher3;

import com.android.launcher3.popup.SystemShortcut;
import com.android.launcher3.uioverrides.QuickstepLauncher;

import java.util.stream.Stream;

/**
 * The Launcher variant used for Android Go Edition
 */
public class Launcher3QuickStepGo extends QuickstepLauncher {
    private static final String TAG = "Launcher3QuickStepGo";

    @Override
    public Stream<SystemShortcut.Factory> getSupportedShortcuts() {
        Stream<SystemShortcut.Factory> shortcuts = super.getSupportedShortcuts();

        if (AppSharing.ENABLE_APP_SHARING) {
            shortcuts = Stream.concat(shortcuts, Stream.of(AppSharing.SHORTCUT_FACTORY));
        }

        return shortcuts;
    }
}
