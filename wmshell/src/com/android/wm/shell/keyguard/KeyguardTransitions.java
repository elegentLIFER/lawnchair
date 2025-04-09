

package com.android.wm.shell.keyguard;

import android.annotation.NonNull;
import android.window.IRemoteTransition;

import com.android.wm.shell.shared.annotations.ExternalThread;

/**
 * Interface exposed to SystemUI Keyguard to register handlers for running
 * animations on keyguard visibility changes.
 *
 * TODO(b/274954192): Merge the occludeTransition and occludeByDream handlers and just let the
 * keyguard handler make the decision on which version it wants to play.
 */
@ExternalThread
public interface KeyguardTransitions {
    /**
     * Registers a set of remote transitions for Keyguard.
     */
    default void register(
            @NonNull IRemoteTransition unlockTransition,
            @NonNull IRemoteTransition appearTransition,
            @NonNull IRemoteTransition occludeTransition,
            @NonNull IRemoteTransition occludeByDreamTransition,
            @NonNull IRemoteTransition unoccludeTransition) {}

    /**
     * Notify whether keyguard has created a remote animation runner for next app launch.
     */
    default void setLaunchingActivityOverLockscreen(boolean isLaunchingActivityOverLockscreen) {}
}
