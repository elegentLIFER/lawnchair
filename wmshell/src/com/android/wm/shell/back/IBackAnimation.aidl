

package com.android.wm.shell.back;

import com.android.internal.view.AppearanceRegion;
import android.view.IRemoteAnimationRunner;
import android.window.IOnBackInvokedCallback;

/**
 * Interface for Launcher process to register back invocation callbacks.
 */
interface IBackAnimation {
    /**
     * Sets a {@link IOnBackInvokedCallback} and a {@link IRemoteAnimationRunner} to be invoked when
     * back navigation has type {@link BackNavigationInfo#TYPE_RETURN_TO_HOME}.
     */
    void setBackToLauncherCallback(in IOnBackInvokedCallback callback,
            in IRemoteAnimationRunner runner);

    /**
     * Clears the previously registered {@link IOnBackInvokedCallback}.
     */
    void clearBackToLauncherCallback();

    /**
     * Uses launcher flags to update the system bar color.
     */
    void customizeStatusBarAppearance(in AppearanceRegion appearance);
}
