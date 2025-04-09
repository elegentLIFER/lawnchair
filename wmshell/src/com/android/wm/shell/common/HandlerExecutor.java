

package com.android.wm.shell.common;

import android.annotation.NonNull;
import android.os.Handler;

/** Executor implementation which is backed by a Handler. */
public class HandlerExecutor implements ShellExecutor {
    private final Handler mHandler;

    public HandlerExecutor(@NonNull Handler handler) {
        mHandler = handler;
    }

    @Override
    public void execute(@NonNull Runnable command) {
        if (mHandler.getLooper().isCurrentThread()) {
            command.run();
            return;
        }
        if (!mHandler.post(command)) {
            throw new RuntimeException(mHandler + " is probably exiting");
        }
    }

    @Override
    public void executeDelayed(@NonNull Runnable r, long delayMillis) {
        if (!mHandler.postDelayed(r, delayMillis)) {
            throw new RuntimeException(mHandler + " is probably exiting");
        }
    }

    @Override
    public void removeCallbacks(@NonNull Runnable r) {
        mHandler.removeCallbacks(r);
    }

    @Override
    public boolean hasCallback(Runnable r) {
        return mHandler.hasCallbacks(r);
    }
}
