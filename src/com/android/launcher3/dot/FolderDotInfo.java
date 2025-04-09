

package com.android.launcher3.dot;

import android.view.ViewDebug;

import com.android.launcher3.Utilities;

/**
 * Subclass of DotInfo that only contains the dot count, which is
 * the sum of all the Folder's items' notifications (each counts as 1).
 */
public class FolderDotInfo extends DotInfo {

    private static final int MIN_COUNT = 0;

    private int mNumNotifications;

    public void addDotInfo(DotInfo dotToAdd) {
        if (dotToAdd == null) {
            return;
        }
        mNumNotifications += dotToAdd.getNotificationKeys().size();
        mNumNotifications = Utilities.boundToRange(
                mNumNotifications, MIN_COUNT, DotInfo.MAX_COUNT);
    }

    public void subtractDotInfo(DotInfo dotToSubtract) {
        if (dotToSubtract == null) {
            return;
        }
        mNumNotifications -= dotToSubtract.getNotificationKeys().size();
        mNumNotifications = Utilities.boundToRange(
                mNumNotifications, MIN_COUNT, DotInfo.MAX_COUNT);
    }

    @Override
    public int getNotificationCount() {
        return mNumNotifications;
    }

    @ViewDebug.ExportedProperty(category = "launcher")
    public boolean hasDot() {
        return mNumNotifications > 0;
    }
}
