

package com.android.wm.shell.common;

import android.util.Pools;
import android.view.SurfaceControl;

/**
 * Provides a synchronized pool of {@link SurfaceControl.Transaction}s to minimize allocations.
 */
public class TransactionPool {
    private final Pools.SynchronizedPool<SurfaceControl.Transaction> mTransactionPool =
            new Pools.SynchronizedPool<>(4);

    public TransactionPool() {
    }

    /** Gets a transaction from the pool. */
    public SurfaceControl.Transaction acquire() {
        SurfaceControl.Transaction t = mTransactionPool.acquire();
        if (t == null) {
            return new SurfaceControl.Transaction();
        }
        return t;
    }

    /**
     * Return a transaction to the pool. DO NOT call {@link SurfaceControl.Transaction#close()} if
     * returning to pool.
     */
    public void release(SurfaceControl.Transaction t) {
        if (!mTransactionPool.release(t)) {
            t.close();
        }
    }
}
