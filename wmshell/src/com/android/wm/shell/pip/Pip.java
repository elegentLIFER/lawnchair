

package com.android.wm.shell.pip;

import android.graphics.Rect;

import com.android.wm.shell.shared.annotations.ExternalThread;

import java.util.function.Consumer;

/**
 * Interface to engage picture in picture feature.
 */
@ExternalThread
public interface Pip {
    /**
     * Expand PIP, it's possible that specific request to activate the window via Alt-tab.
     */
    default void expandPip() {
    }

    /**
     * Called when SysUI state changed.
     *
     * @param isSysUiStateValid Is SysUI state valid or not.
     * @param flag Current SysUI state.
     */
    default void onSystemUiStateChanged(boolean isSysUiStateValid, long flag) {
    }

    /**
     * Set the callback when {@link PipTaskOrganizer#isInPip()} state is changed.
     *
     * @param callback The callback accepts the result of {@link PipTaskOrganizer#isInPip()}
     *                 when it's changed.
     */
    default void setOnIsInPipStateChangedListener(Consumer<Boolean> callback) {}

    /**
     * Called when showing Pip menu.
     */
    default void showPictureInPictureMenu() {}

    /**
     * Called by NavigationBar and TaskbarDelegate in order to listen in for PiP bounds change. This
     * is mostly used for times where the PiP bounds could conflict with SystemUI elements, such as
     * a stashed PiP and the Back-from-Edge gesture.
     */
    default void addPipExclusionBoundsChangeListener(Consumer<Rect> listener) { }

    /**
     * Remove a callback added previously. This is used when NavigationBar is removed from the
     * view hierarchy or destroyed.
     */
    default void removePipExclusionBoundsChangeListener(Consumer<Rect> listener) { }

    /**
     * @return {@link PipTransitionController} instance.
     */
    default PipTransitionController getPipTransitionController() {
        return null;
    }
}
