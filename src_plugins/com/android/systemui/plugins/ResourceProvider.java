
package com.android.systemui.plugins;

import com.android.systemui.plugins.annotations.ProvidesInterface;

/**
 * Plugin to support customizing resource
 */
@ProvidesInterface(action = ResourceProvider.ACTION, version = ResourceProvider.VERSION)
public interface ResourceProvider extends Plugin {
    String ACTION = "com.android.launcher3.action.PLUGIN_DYNAMIC_RESOURCE";
    int VERSION = 1;

    /**
     * @see android.content.res.Resources#getInteger(int)
     */
    int getInt(int resId);

    /**
     * @see android.content.res.Resources#getFraction(int, int, int)
     */
    float getFraction(int resId);

    /**
     * @see android.content.res.Resources#getDimension(int)
     */
    float getDimension(int resId);

    /**
     * @see android.content.res.Resources#getColor(int)
     */
    int getColor(int resId);

    /**
     * @see android.content.res.Resources#getFloat(int)
     */
    float getFloat(int resId);
}
