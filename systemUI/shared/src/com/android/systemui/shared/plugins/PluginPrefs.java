

package com.android.systemui.shared.plugins;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.ArraySet;

import java.util.Set;

/**
 * Storage for all plugin actions in SharedPreferences.
 *
 * This allows the list of actions that the Tuner needs to search for to be generated
 * instead of hard coded.
 */
public class PluginPrefs {

    private static final String PREFS = "plugin_prefs";

    private static final String PLUGIN_ACTIONS = "actions";
    private static final String HAS_PLUGINS = "plugins";

    private final Set<String> mPluginActions;
    private final SharedPreferences mSharedPrefs;

    public PluginPrefs(Context context) {
        mSharedPrefs = context.getSharedPreferences(PREFS, 0);
        mPluginActions = new ArraySet<>(mSharedPrefs.getStringSet(PLUGIN_ACTIONS, null));
    }

    public Set<String> getPluginList() {
        return new ArraySet<>(mPluginActions);
    }

    public synchronized void addAction(String action) {
        if (mPluginActions.add(action)){
            mSharedPrefs.edit().putStringSet(PLUGIN_ACTIONS, mPluginActions).apply();
        }
    }

    public static boolean hasPlugins(Context context) {
        return context.getSharedPreferences(PREFS, 0).getBoolean(HAS_PLUGINS, false);
    }

    public static void setHasPlugins(Context context) {
        context.getSharedPreferences(PREFS, 0).edit().putBoolean(HAS_PLUGINS, true).apply();
    }
}
