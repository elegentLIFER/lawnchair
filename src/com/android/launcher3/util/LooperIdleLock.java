

package com.android.launcher3.util;

import android.os.Looper;
import android.os.MessageQueue;

/**
 * Utility class to block execution until the UI looper is idle.
 */
public class LooperIdleLock implements MessageQueue.IdleHandler {

    private final Object mLock;

    private boolean mIsLocked;
    private Looper mLooper;

    public LooperIdleLock(Object lock, Looper looper) {
        mLock = lock;
        mLooper = looper;
        mIsLocked = true;
        looper.getQueue().addIdleHandler(this);
    }

    @Override
    public boolean queueIdle() {
        synchronized (mLock) {
            mIsLocked = false;
            mLock.notify();
        }
        // Manually remove from the list in case we're calling this outside of the idle callbacks
        // (this is Ok in the normal flow as well because MessageQueue makes a copy of all handlers
        // before calling back)
        mLooper.getQueue().removeIdleHandler(this);
        return false;
    }

    public boolean awaitLocked(long ms) {
        if (mIsLocked) {
            try {
                // Just in case mFlushingWorkerThread changes but we aren't woken up,
                // wait no longer than 1sec at a time
                mLock.wait(ms);
            } catch (InterruptedException ex) {
                // Ignore
            }
        }
        return mIsLocked;
    }
}
