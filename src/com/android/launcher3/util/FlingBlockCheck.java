

package com.android.launcher3.util;

import android.os.SystemClock;

/**
 * Determines whether a fling should be blocked. Currently we block flings when crossing thresholds
 * to new states, and unblock after a short duration.
 */
public class FlingBlockCheck {
    // Allow flinging to a new state after waiting this many milliseconds.
    private static final long UNBLOCK_FLING_PAUSE_DURATION = 200;

    private boolean mBlockFling;
    private long mBlockFlingTime;

    public void blockFling() {
        mBlockFling = true;
        mBlockFlingTime = SystemClock.uptimeMillis();
    }

    public void unblockFling() {
        mBlockFling = false;
        mBlockFlingTime = 0;
    }

    public void onEvent() {
        // We prevent flinging after passing a state, but allow it if the user pauses briefly.
        if (SystemClock.uptimeMillis() - mBlockFlingTime >= UNBLOCK_FLING_PAUSE_DURATION) {
            mBlockFling = false;
        }
    }

    public boolean isBlocked() {
        return mBlockFling;
    }
}
