

package com.android.wm.shell.unfold;

import android.annotation.FloatRange;

import java.util.concurrent.Executor;

/**
 * Wrapper interface for unfold transition progress provider for the Shell
 * @see com.android.systemui.unfold.UnfoldTransitionProgressProvider
 */
public interface ShellUnfoldProgressProvider {

    // This is a temporary workaround until we move the progress providers into the Shell or
    // refactor the dependencies. TLDR, the base module depends on this provider to determine if the
    // FullscreenUnfoldController is available, but this check can't rely on an optional component.
    public static final ShellUnfoldProgressProvider NO_PROVIDER =
            new ShellUnfoldProgressProvider() {};

    /**
     * Adds a transition listener
     */
    default void addListener(Executor executor, UnfoldListener listener) {}

    /**
     * Listener for receiving unfold updates
     */
    interface UnfoldListener {
        default void onStateChangeStarted() {}

        default void onStateChangeProgress(@FloatRange(from = 0.0, to = 1.0) float progress) {}

        default void onStateChangeFinished() {}

        default void onFoldStateChanged(boolean isFolded) {}
    }
}
