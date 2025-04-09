

package com.android.wm.shell;

import static android.view.Display.DEFAULT_DISPLAY;

import android.os.RemoteException;

import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.pip.PinnedStackListenerForwarder;
import com.android.wm.shell.pip.PinnedStackListenerForwarder.PinnedTaskListener;

/**
 * The singleton wrapper to communicate between WindowManagerService and WMShell features
 * (e.g: PIP, SplitScreen, Bubble, OneHandedMode...etc)
 * TODO: Remove once PinnedStackListenerForwarder can be removed
 */
public class WindowManagerShellWrapper {
    private static final String TAG = WindowManagerShellWrapper.class.getSimpleName();

    /**
     * Forwarder to which we can add multiple pinned stack listeners. Each listener will receive
     * updates from the window manager service.
     */
    private final PinnedStackListenerForwarder mPinnedStackListenerForwarder;

    public WindowManagerShellWrapper(ShellExecutor mainExecutor) {
        mPinnedStackListenerForwarder = new PinnedStackListenerForwarder(mainExecutor);
    }

    /**
     * Adds a pinned stack listener, which will receive updates from the window manager service
     * along with any other pinned stack listeners that were added via this method.
     */
    public void addPinnedStackListener(PinnedTaskListener listener)
            throws RemoteException {
        mPinnedStackListenerForwarder.addListener(listener);
        mPinnedStackListenerForwarder.register(DEFAULT_DISPLAY);
    }

    /**
     * Removes a pinned stack listener.
     */
    public void removePinnedStackListener(PinnedTaskListener listener) {
        mPinnedStackListenerForwarder.removeListener(listener);
    }

}
