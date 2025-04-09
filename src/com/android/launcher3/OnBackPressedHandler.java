

package com.android.launcher3;

import androidx.annotation.FloatRange;

/**
 * Interface that mimics {@link android.window.OnBackInvokedCallback} without dependencies on U's
 * API such as {@link android.window.BackEvent}.
 *
 * <p> Impl can assume below order during a back gesture:
 * <ol>
 *  <li> [optional] one {@link #onBackStarted()} will be called to start the gesture
 *  <li> zero or multiple {@link #onBackProgressed(float)} will be called during swipe gesture
 *  <li> either one of {@link #onBackInvoked()} or {@link #onBackCancelled()} will be called to end
 *  the gesture
 */
public interface OnBackPressedHandler{

    /** Called when back has started. */
    default void onBackStarted() {}

    /** Called when back is committed. */
    void onBackInvoked();

    /** Called with back gesture's progress. */
    default void onBackProgressed(@FloatRange(from = 0.0, to = 1.0) float backProgress) {}

    /** Called when user drops the back gesture. */
    default void onBackCancelled() {}
}
