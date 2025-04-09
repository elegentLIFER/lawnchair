

package com.android.wm.shell.common.pip;

/**
 * Listener interface that Launcher attaches to SystemUI to get Pip animation callbacks.
 */
oneway interface IPipAnimationListener {
    /**
     * Notifies the listener that the Pip animation is started.
     */
    void onPipAnimationStarted();

    /**
     * Notifies the listener about PiP resource dimensions changed.
     * Listener can expect an immediate callback the first time they attach.
     *
     * @param cornerRadius the pixel value of the corner radius, zero means it's disabled.
     * @param shadowRadius the pixel value of the shadow radius, zero means it's disabled.
     */
    void onPipResourceDimensionsChanged(int cornerRadius, int shadowRadius);

    /**
     * Notifies the listener that user leaves PiP by tapping on the expand button.
     */
    void onExpandPip();
}
