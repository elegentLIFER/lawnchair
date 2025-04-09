

package com.android.systemui.plugins;

import android.content.Context;
import android.view.View;

public class PluginUtils {

    public static void setId(Context sysuiContext, View view, String id) {
        int i = sysuiContext.getResources().getIdentifier(id, "id", sysuiContext.getPackageName());
        view.setId(i);
    }
}
