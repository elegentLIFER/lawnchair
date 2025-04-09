

package com.android.wm.shell.freeform;

import android.window.WindowContainerTransaction;

/**
 * The interface around {@link FreeformTaskTransitionHandler} for task listeners to start freeform
 * task transitions.
 */
public interface FreeformTaskTransitionStarter {

    /**
     * Starts a windowing mode transition.
     *
     * @param targetWindowingMode the target windowing mode
     * @param wct the {@link WindowContainerTransaction} that changes the windowing mode
     *
     */
    void startWindowingModeTransition(int targetWindowingMode, WindowContainerTransaction wct);

    /**
     * Starts window minimization transition
     *
     * @param wct the {@link WindowContainerTransaction} that changes the windowing mode
     *
     */
    void startMinimizedModeTransition(WindowContainerTransaction wct);

    /**
     * Starts close window transition
     *
     * @param wct the {@link WindowContainerTransaction} that closes the task
     *
     */
    void startRemoveTransition(WindowContainerTransaction wct);
}
