

package com.android.wm.shell.recents;

import android.annotation.Nullable;
import android.graphics.Color;

import com.android.wm.shell.shared.annotations.ExternalThread;
import com.android.wm.shell.util.GroupedRecentTaskInfo;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/**
 * Interface for interacting with the recent tasks.
 */
@ExternalThread
public interface RecentTasks {
    /**
     * Gets the set of recent tasks.
     */
    default void getRecentTasks(int maxNum, int flags, int userId, Executor callbackExecutor,
            Consumer<List<GroupedRecentTaskInfo>> callback) {
    }

    /**
     * Adds the listener to be notified of whether the recent task animation is running.
     */
    default void addAnimationStateListener(Executor listenerExecutor, Consumer<Boolean> listener) {
    }

    /**
     * Sets a background color on the transition root layered behind the outgoing task. {@code null}
     * may be used to clear any previously set colors to avoid showing a background at all. The
     * color is always shown at full opacity.
     */
    default void setTransitionBackgroundColor(@Nullable Color color) {
    }
}
