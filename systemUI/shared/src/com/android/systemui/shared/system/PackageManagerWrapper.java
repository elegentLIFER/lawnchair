

package com.android.systemui.shared.system;

import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.ResolveInfoFlagsBits;
import android.content.pm.ResolveInfo;
import android.os.RemoteException;
import android.os.UserHandle;

import java.util.List;

public class PackageManagerWrapper {

    private static final PackageManagerWrapper sInstance = new PackageManagerWrapper();

    private static final IPackageManager mIPackageManager = AppGlobals.getPackageManager();

    public static final String ACTION_PREFERRED_ACTIVITY_CHANGED =
            Intent.ACTION_PREFERRED_ACTIVITY_CHANGED;

    public static PackageManagerWrapper getInstance() {
        return sInstance;
    }

    private PackageManagerWrapper() {}

    /**
     * @return the activity info for a given {@param componentName} and {@param userId}.
     */
    public ActivityInfo getActivityInfo(ComponentName componentName, int userId) {
        try {
            return mIPackageManager.getActivityInfo(componentName, PackageManager.GET_META_DATA,
                    userId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Report the set of 'Home' activity candidates, plus (if any) which of them
     * is the current "always use this one" setting.
     */
    public ComponentName getHomeActivities(List<ResolveInfo> allHomeCandidates) {
        try {
            return mIPackageManager.getHomeActivities(allHomeCandidates);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Determine the best Activity to perform for a given Intent.
     */
    public ResolveInfo resolveActivity(Intent intent, @ResolveInfoFlagsBits int flags) {
        final String resolvedType =
                intent.resolveTypeIfNeeded(AppGlobals.getInitialApplication().getContentResolver());
        try {
            return mIPackageManager.resolveIntent(
                    intent, resolvedType, flags, UserHandle.getCallingUserId());
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }
}
