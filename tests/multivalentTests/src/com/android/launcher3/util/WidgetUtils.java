
package com.android.launcher3.util;

import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;

import com.android.launcher3.LauncherSettings;
import com.android.launcher3.model.data.LauncherAppWidgetInfo;
import com.android.launcher3.widget.LauncherAppWidgetProviderInfo;
import com.android.launcher3.widget.LauncherWidgetHolder;
import com.android.launcher3.widget.PendingAddWidgetInfo;
import com.android.launcher3.widget.WidgetManagerHelper;

/**
 * Common method for widget binding
 */
public class WidgetUtils {

    /**
     * Creates a LauncherAppWidgetInfo corresponding to {@param info}
     *
     * @param bindWidget if true the info is bound and a valid widgetId is assigned to
     *                   the LauncherAppWidgetInfo
     */
    public static LauncherAppWidgetInfo createWidgetInfo(
            LauncherAppWidgetProviderInfo info, Context targetContext, boolean bindWidget) {
        LauncherAppWidgetInfo item = new LauncherAppWidgetInfo(
                LauncherAppWidgetInfo.NO_ID, info.provider);
        item.spanX = info.minSpanX;
        item.spanY = info.minSpanY;
        item.minSpanX = info.minSpanX;
        item.minSpanY = info.minSpanY;
        item.user = info.getProfile();
        item.cellX = 0;
        item.cellY = 1;
        item.container = LauncherSettings.Favorites.CONTAINER_DESKTOP;

        if (bindWidget) {
            PendingAddWidgetInfo pendingInfo =
                    new PendingAddWidgetInfo(
                            info, LauncherSettings.Favorites.CONTAINER_WIDGETS_TRAY);
            pendingInfo.spanX = item.spanX;
            pendingInfo.spanY = item.spanY;
            pendingInfo.minSpanX = item.minSpanX;
            pendingInfo.minSpanY = item.minSpanY;
            Bundle options = pendingInfo.getDefaultSizeOptions(targetContext);

            LauncherWidgetHolder holder = LauncherWidgetHolder.newInstance(targetContext);
            try {
                int widgetId = holder.allocateAppWidgetId();
                if (!new WidgetManagerHelper(targetContext)
                        .bindAppWidgetIdIfAllowed(widgetId, info, options)) {
                    holder.deleteAppWidgetId(widgetId);
                    throw new IllegalArgumentException("Unable to bind widget id");
                }
                item.appWidgetId = widgetId;
            } finally {
                // Necessary to destroy the holder to free up possible activity context
                holder.destroy();
            }
        }
        return item;
    }

    /**
     * Creates a {@link AppWidgetProviderInfo} for the provided component name
     */
    public static AppWidgetProviderInfo createAppWidgetProviderInfo(ComponentName cn) {
        ActivityInfo activityInfo = new ActivityInfo();
        activityInfo.applicationInfo = new ApplicationInfo();
        AppWidgetProviderInfo info = new AppWidgetProviderInfo();
        info.providerInfo = activityInfo;
        info.provider = cn;
        return info;
    }
}
