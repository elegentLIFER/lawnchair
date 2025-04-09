

package com.android.wm.shell.sysui;

import android.content.Context;
import android.content.pm.UserInfo;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Callbacks for when the user or user's profiles changes.
 */
public interface UserChangeListener {
    /**
     * Called when the current (parent) user changes.
     */
    default void onUserChanged(int newUserId, @NonNull Context userContext) {}

    /**
     * Called when a profile belonging to the user changes.
     */
    default void onUserProfilesChanged(@NonNull List<UserInfo> profiles) {}
}
