

package com.android.quickstep;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Process;
import android.os.UserHandle;
import android.util.Log;

import com.android.launcher3.model.data.AppInfo;
import com.android.launcher3.util.InstantAppResolver;

/**
 * Implementation of InstantAppResolver using platform APIs
 */
@SuppressWarnings("unused")
public class InstantAppResolverImpl extends InstantAppResolver {

    private static final String TAG = "InstantAppResolverImpl";
    public static final String COMPONENT_CLASS_MARKER = "@instantapp";

    private final PackageManager mPM;

    public InstantAppResolverImpl(Context context) {
        mPM = context.getPackageManager();
    }

    @Override
    public boolean isInstantApp(ApplicationInfo info) {
        return info.isInstantApp();
    }

    @Override
    public boolean isInstantApp(AppInfo info) {
        ComponentName cn = info.getTargetComponent();
        return cn != null && cn.getClassName().equals(COMPONENT_CLASS_MARKER);
    }

    @Override
    public boolean isInstantApp(String packageName, int userId) {
        if (!Process.myUserHandle().equals(UserHandle.of(userId))) {
            // Instant app can only exist on current user
            return false;
        }
        try {
            return mPM.isInstantApp(packageName);
        } catch (Exception e) {
            Log.e(TAG, "Failed to determine whether package is instant app " + packageName, e);
            return false;
        }
    }
}
