
package com.android.launcher3.ui;

import static android.os.Process.myUserHandle;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static com.android.launcher3.util.TestUtil.getOnUiThread;

import android.app.Instrumentation;
import android.content.ComponentName;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.android.launcher3.testcomponent.AppWidgetNoConfig;
import com.android.launcher3.testcomponent.AppWidgetWithConfig;
import com.android.launcher3.widget.LauncherAppWidgetProviderInfo;
import com.android.launcher3.widget.WidgetManagerHelper;

import java.util.function.Function;

public class TestViewHelpers {
    private static final String TAG = "TestViewHelpers";

    /**
     * Finds a widget provider which can fit on the home screen.
     *
     * @param hasConfigureScreen if true, a provider with a config screen is returned.
     */
    public static LauncherAppWidgetProviderInfo findWidgetProvider(boolean hasConfigureScreen) {
        LauncherAppWidgetProviderInfo info = getOnUiThread(() -> {
            Instrumentation i = getInstrumentation();
            ComponentName cn = new ComponentName(i.getContext(),
                    hasConfigureScreen ? AppWidgetWithConfig.class : AppWidgetNoConfig.class);
            Log.d(TAG, "findWidgetProvider componentName=" + cn.flattenToString());
            return new WidgetManagerHelper(i.getTargetContext()).findProvider(cn, myUserHandle());
        });
        if (info == null) {
            throw new IllegalArgumentException("No valid widget provider");
        }
        return info;
    }

    public static View findChildView(ViewGroup parent, Function<View, Boolean> condition) {
        for (int i = 0; i < parent.getChildCount(); ++i) {
            final View child = parent.getChildAt(i);
            if (condition.apply(child)) return child;
        }
        return null;
    }
}
