

package com.android.launcher3.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.android.launcher3.R;
import com.android.launcher3.model.data.AppInfo;

/**
 * A wrapper class to access instant app related APIs.
 */
public class InstantAppResolver implements ResourceBasedOverride {

    public static InstantAppResolver newInstance(Context context) {
        return Overrides.getObject(
                InstantAppResolver.class, context, R.string.instant_app_resolver_class);
    }

    public boolean isInstantApp(ApplicationInfo info) {
        return false;
    }

    public boolean isInstantApp(AppInfo info) {
        return false;
    }

    public boolean isInstantApp(String packageName, int userId) {
        return false;
    }
}
