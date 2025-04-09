

package com.android.systemui.plugin.testoverlayplugin;

import android.annotation.Nullable;
import android.app.Activity;
import android.os.Bundle;

/**
 * DO NOT Reference Plugin interfaces here, this runs in the plugin APK's process
 * and is only for modifying settings.
 */
public class PluginSettings extends Activity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plugin_settings);
    }
}
