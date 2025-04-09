

package com.android.wm.shell.sysui;

import android.content.res.Configuration;

/**
 * Callbacks for when the configuration changes.
 */
public interface ConfigurationChangeListener {

    /**
     * Called when a configuration changes. This precedes all the following callbacks.
     */
    default void onConfigurationChanged(Configuration newConfiguration) {}

    /**
     * Convenience method to the above, called when the density or font scale changes.
     */
    default void onDensityOrFontScaleChanged() {}

    /**
     * Convenience method to the above, called when the smallest screen width changes.
     */
    default void onSmallestScreenWidthChanged() {}

    /**
     * Convenience method to the above, called when the system theme changes, including dark/light
     * UI_MODE changes.
     */
    default void onThemeChanged() {}

    /**
     * Convenience method to the above, called when the local list or layout direction changes.
     */
    default void onLocaleOrLayoutDirectionChanged() {}
}
