

package com.android.wm.shell.common;

import android.content.Context;

/**
 * An interface for controllers (of type T) that can receive remote calls.
 */
public interface RemoteCallable<T> {
    /**
     * Returns a context used for permission checking.
     */
    Context getContext();

    /**
     * Returns the executor to post the handler callback to.
     */
    ShellExecutor getRemoteCallExecutor();
}
