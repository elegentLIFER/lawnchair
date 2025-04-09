
package com.android.app.viewcapture;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RunnableFuture;

/**
 * Implementation of {@link Executor} which executes on a provided looper.
 */
public class LooperExecutor implements Executor {

    private final Handler mHandler;

    public LooperExecutor(Looper looper) {
        mHandler = new Handler(looper);
    }

    @Override
    public void execute(Runnable runnable) {
        if (mHandler.getLooper() == Looper.myLooper()) {
            runnable.run();
        } else {
            mHandler.post(runnable);
        }
    }

    /**
     * @throws RejectedExecutionException {@inheritDoc}
     * @throws NullPointerException       {@inheritDoc}
     */
    public <T> Future<T> submit(Callable<T> task) {
        if (task == null) throw new NullPointerException();
        RunnableFuture<T> ftask = new FutureTask<T>(task);
        execute(ftask);
        return ftask;
    }

}
