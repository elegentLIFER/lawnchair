

package com.android.launcher3.testcomponent;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * A simple app widget with shows a dialog on clicking.
 */
public class AppWidgetWithDialog extends AppWidgetNoConfig {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        int layoutId = context.getResources().getIdentifier(
                "test_layout_appwidget_blue", "layout", context.getPackageName());
        RemoteViews views = new RemoteViews(context.getPackageName(), layoutId);

        PendingIntent pi = PendingIntent.getActivity(context, 0,
                new Intent(context, DialogTestActivity.class), PendingIntent.FLAG_IMMUTABLE);
        views.setOnClickPendingIntent(android.R.id.content, pi);
        AppWidgetManager.getInstance(context).updateAppWidget(appWidgetIds, views);
    }
}
