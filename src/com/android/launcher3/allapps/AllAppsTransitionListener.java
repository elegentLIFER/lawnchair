
package com.android.launcher3.allapps;

/**
 * An interface for listening to all-apps open-close transition
 */
public interface AllAppsTransitionListener {
    /**
     * Called when the transition starts
     * @param toAllApps {@code true} if this transition is supposed to end in the AppApps UI
     *
     * @see ActivityAllAppsContainerView
     */
    void onAllAppsTransitionStart(boolean toAllApps);

    /**
     * Called when the transition ends
     * @param toAllApps {@code true} if the final state is all-apps
     *
     * @see ActivityAllAppsContainerView
     */
    void onAllAppsTransitionEnd(boolean toAllApps);
}
