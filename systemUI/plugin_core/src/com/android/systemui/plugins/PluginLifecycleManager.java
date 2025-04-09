

package com.android.systemui.plugins;

import android.content.ComponentName;

import java.util.function.BiConsumer;

/**
 * Provides the ability for consumers to control plugin lifecycle.
 *
 * @param <T> is the target plugin type
 */
public interface PluginLifecycleManager<T extends Plugin> {
    /** Returns the ComponentName of the target plugin. Maybe be called when not loaded. */
    ComponentName getComponentName();

    /** Returns the package name of the target plugin. May be called when not loaded. */
    String getPackage();

    /** Returns the currently loaded plugin instance (if plugin is loaded) */
    T getPlugin();

    /** Log tag and messages will be sent to the provided Consumer */
    void setLogFunc(BiConsumer<String, String> logConsumer);

    /** returns true if the plugin is currently loaded */
    default boolean isLoaded() {
        return getPlugin() != null;
    }

    /**
     * Loads and creates the plugin instance if it does not exist.
     *
     * This will trigger {@link PluginListener#onPluginLoaded} with the new instance if it did not
     * already exist.
     */
    void loadPlugin();

    /**
     * Unloads and destroys the plugin instance if it exists.
     *
     * This will trigger {@link PluginListener#onPluginUnloaded} if a concrete plugin instance
     * existed when this call was made.
     */
    void unloadPlugin();
}
