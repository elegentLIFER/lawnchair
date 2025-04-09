

package com.android.wm.shell;

import com.android.wm.shell.common.ShellExecutor;

import java.util.ArrayList;

/**
 * Really basic test executor. It just gathers all events in a blob. The only option is to
 * execute everything at once. If better control over delayed execution is needed, please add it.
 */
public class TestShellExecutor implements ShellExecutor {
    final ArrayList<Runnable> mRunnables = new ArrayList<>();

    @Override
    public void execute(Runnable runnable) {
        mRunnables.add(runnable);
    }

    @Override
    public void executeDelayed(Runnable r, long delayMillis) {
        mRunnables.add(r);
    }

    @Override
    public void removeCallbacks(Runnable r) {
        mRunnables.remove(r);
    }

    @Override
    public boolean hasCallback(Runnable r) {
        return mRunnables.contains(r);
    }

    public void flushAll() {
        while (!mRunnables.isEmpty()) {
            mRunnables.remove(0).run();
        }
    }
}
