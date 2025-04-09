

package com.android.wm.shell.shared;

import android.window.RemoteTransition;
import android.window.TransitionFilter;

/**
 * Listener interface that Launcher attaches to SystemUI to get home activity transition callbacks
 * on the default display.
 */
oneway interface IHomeTransitionListener {

    /**
     * Called when a transition changes the visibility of the home activity on the default display.
     */
    void onHomeVisibilityChanged(in boolean isVisible);
}

