
package com.android.quickstep.util;

import static app.lawnchair.util.LawnchairUtilsKt.supportsRoundedCornersOnWindows;

import android.content.Context;
import android.content.res.Resources;

import com.android.launcher3.R;
import com.android.launcher3.util.Themes;

public class TaskCornerRadius {

    public static float get(Context context) {
        Resources resources = context.getResources();
        if (!supportsRoundedCornersOnWindows(context)) {
            return resources.getDimension(R.dimen.task_corner_radius_small);
        }

        float overriddenRadius =
                resources.getDimension(R.dimen.task_corner_radius_override);
        return (overriddenRadius > 0) ? overriddenRadius : Themes.getDialogCornerRadius(context);
    }
}
