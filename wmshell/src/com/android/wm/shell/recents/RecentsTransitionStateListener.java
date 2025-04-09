

package com.android.wm.shell.recents;

import android.os.IBinder;

/** The listener for the events from {@link RecentsTransitionHandler}. */
public interface RecentsTransitionStateListener {

    /** Notifies whether the recents animation is running. */
    default void onAnimationStateChanged(boolean running) {
    }

    /** Notifies that a recents shell transition has started. */
    default void onTransitionStarted(IBinder transition) {}
}
