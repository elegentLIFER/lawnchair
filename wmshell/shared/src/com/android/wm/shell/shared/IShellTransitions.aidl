

package com.android.wm.shell.shared;

import android.view.SurfaceControl;
import android.window.RemoteTransition;
import android.window.TransitionFilter;

import com.android.wm.shell.shared.IHomeTransitionListener;

/**
 * Interface that is exposed to remote callers to manipulate the transitions feature.
 */
interface IShellTransitions {

    /**
     * Registers a remote transition handler for all operations excluding takeovers (see
     * registerRemoteForTakeover()).
     */
    oneway void registerRemote(in TransitionFilter filter,
            in RemoteTransition remoteTransition) = 1;

    /**
     * Unregisters a remote transition handler for all operations.
     */
    oneway void unregisterRemote(in RemoteTransition remoteTransition) = 2;

    /**
     * Retrieves the apply-token used by transactions in Shell
     */
    IBinder getShellApplyToken() = 3;

    /**
     * Set listener that will receive callbacks about transitions involving home activity.
     */
    oneway void setHomeTransitionListener(in IHomeTransitionListener listener) = 4;

    /**
     * Returns a container surface for the home root task.
     */
    SurfaceControl getHomeTaskOverlayContainer() = 5;

    /**
     * Registers a remote transition for takeover operations only.
     */
    oneway void registerRemoteForTakeover(in TransitionFilter filter,
            in RemoteTransition remoteTransition) = 6;
}
