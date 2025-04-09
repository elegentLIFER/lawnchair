

package com.android.systemui.plugins;

import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;

import com.android.systemui.plugins.annotations.ProvidesInterface;

/**
 * Implement this plugin interface to add a custom widget.
 */
@ProvidesInterface(action = CustomWidgetPlugin.ACTION, version = CustomWidgetPlugin.VERSION)
public interface CustomWidgetPlugin extends Plugin {

    String ACTION = "com.android.systemui.action.PLUGIN_CUSTOM_WIDGET";
    int VERSION = 1;

    /**
     * Notify the plugin that container of the widget has been rendered, where the custom widget
     * can be attached to.
     */
    void onViewCreated(AppWidgetHostView parent);

    /**
     * Get the UUID for the custom widget.
     *
     * @deprecated Not used
     */
    @Deprecated
    default String getId() {
        return "";
    }

    /**
     * Used to modify a widgets' info.
     */
    default void updateWidgetInfo(AppWidgetProviderInfo info, Context context) { }
}
