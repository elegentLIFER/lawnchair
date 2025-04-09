

package com.android.wm.shell.onehanded;

import static com.android.wm.shell.onehanded.OneHandedSettingsUtil.ONE_HANDED_TIMEOUT_MEDIUM_IN_SECONDS;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.android.wm.shell.common.ShellExecutor;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Timeout handler for stop one handed mode operations.
 */
public class OneHandedTimeoutHandler {
    private static final String TAG = "OneHandedTimeoutHandler";

    private final ShellExecutor mMainExecutor;

    // Default timeout is ONE_HANDED_TIMEOUT_MEDIUM
    private @OneHandedSettingsUtil.OneHandedTimeout int mTimeout =
            ONE_HANDED_TIMEOUT_MEDIUM_IN_SECONDS;
    private long mTimeoutMs = TimeUnit.SECONDS.toMillis(mTimeout);
    private final Runnable mTimeoutRunnable = this::onStop;
    private List<TimeoutListener> mListeners = new ArrayList<>();

    /**
     * Get the current config of timeout
     *
     * @return timeout of current config
     */
    public @OneHandedSettingsUtil.OneHandedTimeout int getTimeout() {
        return mTimeout;
    }

    /**
     * Listens for notify timeout events
     */
    public interface TimeoutListener {
        /**
         * Called whenever the config time out
         *
         * @param timeoutTime The time in seconds to trigger timeout
         */
        void onTimeout(int timeoutTime);
    }

    public OneHandedTimeoutHandler(ShellExecutor mainExecutor) {
        mMainExecutor = mainExecutor;
    }

    /**
     * Set the specific timeout of {@link OneHandedSettingsUtil.OneHandedTimeout}
     */
    public void setTimeout(@OneHandedSettingsUtil.OneHandedTimeout int timeout) {
        mTimeout = timeout;
        mTimeoutMs = TimeUnit.SECONDS.toMillis(mTimeout);
        resetTimer();
    }

    /**
     * Reset the timer when one handed trigger or user is operating in some conditions
     */
    public void removeTimer() {
        mMainExecutor.removeCallbacks(mTimeoutRunnable);
    }

    /**
     * Reset the timer when one handed trigger or user is operating in some conditions
     */
    public void resetTimer() {
        removeTimer();
        if (mTimeout == OneHandedSettingsUtil.ONE_HANDED_TIMEOUT_NEVER) {
            return;
        }
        if (mTimeout != OneHandedSettingsUtil.ONE_HANDED_TIMEOUT_NEVER) {
            mMainExecutor.executeDelayed(mTimeoutRunnable, mTimeoutMs);
        }
    }

    /**
     * Register timeout listener to receive time out events
     *
     * @param listener the listener be sent events when times up
     */
    public void registerTimeoutListener(TimeoutListener listener) {
        mListeners.add(listener);
    }

    @VisibleForTesting
    boolean hasScheduledTimeout() {
        return mMainExecutor.hasCallback(mTimeoutRunnable);
    }

    private void onStop() {
        for (int i = mListeners.size() - 1; i >= 0; i--) {
            final TimeoutListener listener = mListeners.get(i);
            listener.onTimeout(mTimeout);
        }
    }

    void dump(@NonNull PrintWriter pw) {
        final String innerPrefix = "  ";
        pw.println(TAG);
        pw.print(innerPrefix + "sTimeout=");
        pw.println(mTimeout);
        pw.print(innerPrefix + "sListeners=");
        pw.println(mListeners);
    }

}
