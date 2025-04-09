
package com.android.wm.shell.transition;

import android.os.IBinder;
import android.os.RemoteException;
import android.view.SurfaceControl;
import android.window.IRemoteTransition;
import android.window.IRemoteTransitionFinishedCallback;
import android.window.RemoteTransitionStub;
import android.window.TransitionInfo;
import android.window.WindowContainerTransaction;

/**
 * {@link IRemoteTransition} for testing purposes.
 * Stores info about
 * {@link #startAnimation(IBinder, TransitionInfo, SurfaceControl.Transaction,
 * IRemoteTransitionFinishedCallback)} being called.
 */
public class TestRemoteTransition extends RemoteTransitionStub {
    private boolean mCalled = false;
    private boolean mConsumed = false;
    final WindowContainerTransaction mRemoteFinishWCT = new WindowContainerTransaction();

    @Override
    public void startAnimation(IBinder transition, TransitionInfo info,
            SurfaceControl.Transaction startTransaction,
            IRemoteTransitionFinishedCallback finishCallback)
            throws RemoteException {
        mCalled = true;
        finishCallback.onTransitionFinished(mRemoteFinishWCT, null /* sct */);
    }

    @Override
    public void onTransitionConsumed(IBinder iBinder, boolean b) throws RemoteException {
        mConsumed = true;
    }

    /**
     * Check whether this remote transition
     * {@link #startAnimation(IBinder, TransitionInfo, SurfaceControl.Transaction,
     * IRemoteTransitionFinishedCallback)} is called
     */
    public boolean isCalled() {
        return mCalled;
    }

    /**
     * Check whether this remote transition's {@link #onTransitionConsumed(IBinder, boolean)}
     * is called
     */
    public boolean isConsumed() {
        return mConsumed;
    }
}
