
package com.android.launcher3.util;

import static com.android.launcher3.util.MainThreadInitializedObject.forOverride;

import com.android.launcher3.R;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.PluginListener;

import java.io.PrintWriter;

public class PluginManagerWrapper implements ResourceBasedOverride, SafeCloseable {

    public static final MainThreadInitializedObject<PluginManagerWrapper> INSTANCE =
            forOverride(PluginManagerWrapper.class, R.string.plugin_manager_wrapper_class);

    public <T extends Plugin> void addPluginListener(
            PluginListener<T> listener, Class<T> pluginClass) {
        addPluginListener(listener, pluginClass, false);
    }

    public <T extends Plugin> void addPluginListener(
            PluginListener<T> listener, Class<T> pluginClass, boolean allowMultiple) {
    }

    public void removePluginListener(PluginListener<? extends Plugin> listener) { }

    @Override
    public void close() { }

    public void dump(PrintWriter pw) { }
}
