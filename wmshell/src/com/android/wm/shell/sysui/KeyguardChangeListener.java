

package com.android.wm.shell.sysui;

/**
 * Callbacks for when the keyguard changes.
 */
public interface KeyguardChangeListener {
    /**
     * Called when the keyguard is showing (and if so, whether it is occluded).
     */
    default void onKeyguardVisibilityChanged(boolean visible, boolean occluded,
            boolean animatingDismiss) {}

    /**
     * Called when the keyguard dismiss animation has finished.
     *
     * TODO(b/206741900) deprecate this path once we're able to animate the PiP window as part of
     * keyguard dismiss animation.
     */
    default void onKeyguardDismissAnimationFinished() {}
}
