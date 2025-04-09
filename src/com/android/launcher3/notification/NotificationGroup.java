

package com.android.launcher3.notification;

import java.util.HashSet;
import java.util.Set;

/**
 * Contains data related to a group of notifications, like the group summary key and the child keys.
 */
public class NotificationGroup {
    private String mGroupSummaryKey;
    private Set<String> mChildKeys;

    public NotificationGroup() {
        mChildKeys = new HashSet<>();
    }

    public void setGroupSummaryKey(String groupSummaryKey) {
        mGroupSummaryKey = groupSummaryKey;
    }

    public String getGroupSummaryKey() {
        return mGroupSummaryKey;
    }

    public void addChildKey(String childKey) {
        mChildKeys.add(childKey);
    }

    public void removeChildKey(String childKey) {
        mChildKeys.remove(childKey);
    }

    public boolean isEmpty() {
        return mChildKeys.isEmpty();
    }
}
