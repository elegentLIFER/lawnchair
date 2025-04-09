

package android.window;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;

/**
 * Callback allowing applications to handle back events in place of the system.
 *
 * <p>Callback instances can be added to and removed from {@link OnBackInvokedDispatcher}, which is
 * held at window level and accessible through {@link Activity#getOnBackInvokedDispatcher()}, {@link
 * Dialog#getOnBackInvokedDispatcher()}, {@link Window#getOnBackInvokedDispatcher()} and {@link
 * View#findOnBackInvokedDispatcher()}.
 *
 * <p>When back is triggered, callbacks on the in-focus window are invoked in reverse order in which
 * they are added within the same priority. Between different priorities, callbacks with higher
 * priority are invoked first.
 *
 * <p>This replaces {@link Activity#onBackPressed()}, {@link Dialog#onBackPressed()} and {@link
 * android.view.KeyEvent#KEYCODE_BACK}
 *
 * <p>If you want to customize back animation behaviors, in addition to handling back invocations,
 * register its subclass instances {@link OnBackAnimationCallback} instead.
 *
 * <p>
 *
 * @see OnBackInvokedDispatcher#registerOnBackInvokedCallback(int, OnBackInvokedCallback)
 *     registerOnBackInvokedCallback(priority, OnBackInvokedCallback) to specify callback priority.
 */
@SuppressWarnings("deprecation")
public interface OnBackInvokedCallback {
    /**
     * Called when a back gesture has been completed and committed, or back button pressed has been
     * released and committed.
     */
    void onBackInvoked();
}
