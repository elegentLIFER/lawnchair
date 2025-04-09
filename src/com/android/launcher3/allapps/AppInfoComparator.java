
package com.android.launcher3.allapps;

import android.content.Context;
import android.os.Process;
import android.os.UserHandle;
import android.text.TextUtils;

import com.android.launcher3.model.data.AppInfo;
import com.android.launcher3.pm.UserCache;
import com.android.launcher3.util.LabelComparator;

import java.util.Comparator;
import java.util.Locale;

/**
 * A comparator to arrange items based on user profiles.
 */
public class AppInfoComparator implements Comparator<AppInfo> {

    private final UserCache mUserManager;
    private final UserHandle mMyUser;
    private final LabelComparator mLabelComparator;

    public AppInfoComparator(Context context) {
        mUserManager = UserCache.INSTANCE.get(context);
        mMyUser = Process.myUserHandle();
        mLabelComparator = new LabelComparator();
    }

    @Override
    public int compare(AppInfo a, AppInfo b) {
        // Order by the title in the current locale
        int result = mLabelComparator.compare(
                a.title == null ? "" : a.title.toString(),
                b.title == null ? "" : b.title.toString());
        // Group app list by sectionName before sorting for Simplified Chinese only
        if (isSimpledChineseLocale()) {
            result += a.sectionName.compareTo(b.sectionName) * 10;
        }
        if (result != 0) {
            return result;
        }

        // If labels are same, compare component names
        result = a.componentName.compareTo(b.componentName);
        if (result != 0) {
            return result;
        }

        if (mMyUser.equals(a.user)) {
            return -1;
        } else {
            Long aUserSerial = mUserManager.getSerialNumberForUser(a.user);
            Long bUserSerial = mUserManager.getSerialNumberForUser(b.user);
            return aUserSerial.compareTo(bUserSerial);
        }
    }

    private boolean isSimpledChineseLocale() {
        final Locale defaultLocale = Locale.getDefault();
        return "zh".equals(defaultLocale.getLanguage()) &&
                ("CN".equals(defaultLocale.getCountry()) || "Hans".equals(defaultLocale.getScript()));
    }
}
