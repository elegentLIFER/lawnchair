

package com.android.wm.shell.pip;

import android.content.ComponentName;
import android.os.RemoteException;
import android.view.IPinnedTaskListener;
import android.view.WindowManagerGlobal;

import androidx.annotation.BinderThread;

import com.android.wm.shell.common.ShellExecutor;

import java.util.ArrayList;

/**
 * PinnedStackListener that simply forwards all calls to each listener added via
 * {@link #addListener}. This is necessary since calling
 * {@link com.android.server.wm.WindowManagerService#registerPinnedTaskListener} replaces any
 * previously set listener.
 */
public class PinnedStackListenerForwarder {

    private final IPinnedTaskListener mListenerImpl = new PinnedTaskListenerImpl();
    private final ShellExecutor mMainExecutor;
    private final ArrayList<PinnedTaskListener> mListeners = new ArrayList<>();

    public PinnedStackListenerForwarder(ShellExecutor mainExecutor) {
        mMainExecutor = mainExecutor;
    }

    /** Adds a listener to receive updates from the WindowManagerService. */
    public void addListener(PinnedTaskListener listener) {
        mListeners.add(listener);
    }

    /** Removes a listener so it will no longer receive updates from the WindowManagerService. */
    public void removeListener(PinnedTaskListener listener) {
        mListeners.remove(listener);
    }

    public void register(int displayId) throws RemoteException {
        WindowManagerGlobal.getWindowManagerService().registerPinnedTaskListener(
                displayId, mListenerImpl);
    }

    private void onMovementBoundsChanged(boolean fromImeAdjustment) {
        for (PinnedTaskListener listener : mListeners) {
            listener.onMovementBoundsChanged(fromImeAdjustment);
        }
    }

    private void onImeVisibilityChanged(boolean imeVisible, int imeHeight) {
        for (PinnedTaskListener listener : mListeners) {
            listener.onImeVisibilityChanged(imeVisible, imeHeight);
        }
    }

    private void onActivityHidden(ComponentName componentName) {
        for (PinnedTaskListener listener : mListeners) {
            listener.onActivityHidden(componentName);
        }
    }

    @BinderThread
    private class PinnedTaskListenerImpl extends IPinnedTaskListener.Stub {
        @Override
        public void onMovementBoundsChanged(boolean fromImeAdjustment) {
            mMainExecutor.execute(() -> {
                PinnedStackListenerForwarder.this.onMovementBoundsChanged(fromImeAdjustment);
            });
        }

        @Override
        public void onImeVisibilityChanged(boolean imeVisible, int imeHeight) {
            mMainExecutor.execute(() -> {
                PinnedStackListenerForwarder.this.onImeVisibilityChanged(imeVisible, imeHeight);
            });
        }

        @Override
        public void onActivityHidden(ComponentName componentName) {
            mMainExecutor.execute(() -> {
                PinnedStackListenerForwarder.this.onActivityHidden(componentName);
            });
        }
    }

    /**
     * A counterpart of {@link IPinnedTaskListener} with empty implementations.
     * Subclasses can ignore those methods they do not intend to take action upon.
     */
    public static class PinnedTaskListener {
        public void onMovementBoundsChanged(boolean fromImeAdjustment) {}

        public void onImeVisibilityChanged(boolean imeVisible, int imeHeight) {}

        public void onActivityHidden(ComponentName componentName) {}
    }
}
