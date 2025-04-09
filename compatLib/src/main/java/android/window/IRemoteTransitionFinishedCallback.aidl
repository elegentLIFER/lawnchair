
package android.window;
import android.view.SurfaceControl;
import android.window.WindowContainerTransaction;
/**
 * Interface to be invoked by the controlling process when a remote transition has finished.
 *
 * @see IRemoteTransition
 * @param wct An optional WindowContainerTransaction to apply before the transition finished.
 * @param sct An optional Surface Transaction that is added to the end of the finish/cleanup
 *            transaction. This is applied by shell.Transitions (before submitting the wct).
 * {@hide}
 */
interface IRemoteTransitionFinishedCallback {
    void onTransitionFinished(in WindowContainerTransaction wct, in SurfaceControl.Transaction sct);
}
