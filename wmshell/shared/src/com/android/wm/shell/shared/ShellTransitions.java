

package com.android.wm.shell.shared;

import android.annotation.NonNull;
import android.window.RemoteTransition;
import android.window.TransitionFilter;

import com.android.wm.shell.shared.annotations.ExternalThread;

/**
 * Interface to manage remote transitions.
 */
@ExternalThread
public interface ShellTransitions {
    /**
     * Registers a remote transition for all operations excluding takeovers (see
     * {@link ShellTransitions#registerRemoteForTakeover(TransitionFilter, RemoteTransition)}).
     */
    default void registerRemote(@NonNull TransitionFilter filter,
            @NonNull RemoteTransition remoteTransition) {}

    /**
     * Registers a remote transition for takeover operations only.
     */
    default void registerRemoteForTakeover(@NonNull TransitionFilter filter,
            @NonNull RemoteTransition remoteTransition) {}

    /**
     * Unregisters a remote transition for all operations.
     */
    default void unregisterRemote(@NonNull RemoteTransition remoteTransition) {}
}
