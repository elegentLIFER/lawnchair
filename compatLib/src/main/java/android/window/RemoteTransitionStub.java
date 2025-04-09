

package android.window;

import android.os.IBinder;
import android.os.RemoteException;
import android.view.SurfaceControl;

/**
 * Utility base implementation of {@link IRemoteTransition} that users can extend to avoid stubbing.
 *
 * @hide
 */
public abstract class RemoteTransitionStub extends IRemoteTransition.Stub {
    @Override
    public void mergeAnimation(
            IBinder transition,
            TransitionInfo info,
            SurfaceControl.Transaction t,
            IBinder mergeTarget,
            IRemoteTransitionFinishedCallback finishCallback)
            throws RemoteException {}

    @Override
    public void takeOverAnimation(
            IBinder transition,
            TransitionInfo info,
            SurfaceControl.Transaction startTransaction,
            IRemoteTransitionFinishedCallback finishCallback,
            WindowAnimationState[] states)
            throws RemoteException {
        throw new RemoteException("Takeovers are not supported by this IRemoteTransition");
    }

    @Override
    public void onTransitionConsumed(IBinder transition, boolean aborted) throws RemoteException {}
}
