

package com.android.systemui.plugins;

import com.android.systemui.plugins.annotations.ProvidesInterface;

@ProvidesInterface(version = PluginDependency.VERSION)
public class PluginDependency {
    public static final int VERSION = 1;
    static DependencyProvider sProvider;

    public static <T> T get(Plugin p, Class<T> cls) {
        return sProvider.get(p, cls);
    }

    static abstract class DependencyProvider {
        abstract <T> T get(Plugin p, Class<T> cls);
    }
}
