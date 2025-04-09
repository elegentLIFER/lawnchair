
package com.android.launcher3.pm;

import android.content.ComponentName;
import android.content.pm.PackageInstaller;
import android.os.UserHandle;

import androidx.annotation.NonNull;

public final class PackageInstallInfo {
    private static final String TAG = "PackageInstallInfo";

    public static final int STATUS_INSTALLED = 0;
    public static final int STATUS_INSTALLING = 1;
    public static final int STATUS_INSTALLED_DOWNLOADING = 2;
    public static final int STATUS_FAILED = 3;

    public final ComponentName componentName;
    public final String packageName;
    public final int state;
    public final int progress;
    public final UserHandle user;

    private PackageInstallInfo(@NonNull PackageInstaller.SessionInfo info) {
        this.state = STATUS_INSTALLING;
        this.packageName = info.getAppPackageName();
        this.componentName = new ComponentName(packageName, "");
        this.progress = (int) (info.getProgress() * 100f);
        this.user = InstallSessionHelper.getUserHandle(info);
    }

    public PackageInstallInfo(String packageName, int state, int progress, UserHandle user) {
        this.state = state;
        this.packageName = packageName;
        this.componentName = new ComponentName(packageName, "");
        this.progress = progress;
        this.user = user;
    }

    public static PackageInstallInfo fromInstallingState(PackageInstaller.SessionInfo info) {
        return new PackageInstallInfo(info);
    }

    public static PackageInstallInfo fromState(int state, String packageName, UserHandle user) {
        return new PackageInstallInfo(packageName, state, 0 /* progress */, user);
    }


    @Override
    public String toString() {
        return TAG + "(" + dumpProperties() + ")";
    }

    private String dumpProperties() {
        return "componentName=" + componentName
                + "packageName=" + packageName
                + " state=" + stateToString()
                + " progress=" + progress
                + " user=" + user;
    }

    private String stateToString() {
        switch (state) {
            case STATUS_INSTALLED : return "STATUS_INSTALLED";
            case STATUS_INSTALLING : return "STATUS_INSTALLING";
            case STATUS_INSTALLED_DOWNLOADING : return "STATUS_INSTALLED_DOWNLOADING";
            case STATUS_FAILED : return "STATUS_FAILED";
            default : return "INVALID STATE";
        }
    }
}
