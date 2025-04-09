

package com.android.launcher3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageInstaller.SessionInfo;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import android.text.TextUtils;

import androidx.annotation.WorkerThread;

import com.android.launcher3.logging.FileLog;
import com.android.launcher3.model.ItemInstallQueue;
import com.android.launcher3.pm.InstallSessionHelper;
import com.android.launcher3.pm.UserCache;
import com.android.launcher3.util.Executors;
import com.patrykmichalik.opto.core.PreferenceExtensionsKt;

import app.lawnchair.preferences2.PreferenceManager2;

import java.util.Locale;

import java.util.Locale;

/**
 * BroadcastReceiver to handle session commit intent.
 */
public class SessionCommitReceiver extends BroadcastReceiver {

    private static final String LOG = "SessionCommitReceiver";

    // Preference key for automatically adding icon to homescreen.
    public static final String ADD_ICON_PREFERENCE_KEY = "pref_add_icon_to_home";

    @Override
    public void onReceive(Context context, Intent intent) {
        Executors.MODEL_EXECUTOR.execute(() -> processIntent(context, intent));
    }

    @WorkerThread
    private static void processIntent(Context context, Intent intent) {
        UserHandle user = intent.getParcelableExtra(Intent.EXTRA_USER);
        if (!isEnabled(context)) {
            // User has decided to not add icons on homescreen.
            return;
        }

        SessionInfo info = intent.getParcelableExtra(PackageInstaller.EXTRA_SESSION);
        if (!PackageInstaller.ACTION_SESSION_COMMITTED.equals(intent.getAction())
                || info == null || user == null) {
            // Invalid intent.
            return;
        }

        InstallSessionHelper packageInstallerCompat = InstallSessionHelper.INSTANCE.get(context);
        boolean alreadyAddedPromiseIcon = packageInstallerCompat.promiseIconAddedForId(info.getSessionId());
        if (TextUtils.isEmpty(info.getAppPackageName())
                || info.getInstallReason() != PackageManager.INSTALL_REASON_USER
                || alreadyAddedPromiseIcon) {
            FileLog.d(LOG,
                    String.format(Locale.ENGLISH,
                            "Removing PromiseIcon for package: %s, install reason: %d,"
                                    + " alreadyAddedPromiseIcon: %s",
                            info.getAppPackageName(),
                            info.getInstallReason(),
                            alreadyAddedPromiseIcon));
            packageInstallerCompat.removePromiseIconId(info.getSessionId());
            return;
        }

        FileLog.d(LOG,
                "Adding package name to install queue. Package name: " + info.getAppPackageName()
                        + ", has app icon: " + (info.getAppIcon() != null)
                        + ", has app label: " + !TextUtils.isEmpty(info.getAppLabel()));

        ItemInstallQueue.INSTANCE.get(context)
                .queueItem(info.getAppPackageName(), user);
    }

    public static boolean isEnabled(Context context) {
        if (PreferenceExtensionsKt.firstBlocking(PreferenceManager2.getInstance(context).getLockHomeScreen()))
            return false;
        return Utilities.getPrefs(context).getBoolean(ADD_ICON_PREFERENCE_KEY, true);
    }
}
