

package com.android.wm.shell.transition.tracing;

import com.android.wm.shell.transition.Transitions;

public interface TransitionTracer {
    /**
     * Adds an entry in the trace to log that a transition has been dispatched to a handler.
     *
     * @param transitionId The id of the transition being dispatched.
     * @param handler The handler the transition is being dispatched to.
     */
    void logDispatched(int transitionId, Transitions.TransitionHandler handler);

    /**
     * Adds an entry in the trace to log that a request to merge a transition was made.
     *
     * @param mergeRequestedTransitionId The id of the transition we are requesting to be merged.
     */
    void logMergeRequested(int mergeRequestedTransitionId, int playingTransitionId);

    /**
     * Adds an entry in the trace to log that a transition was merged by the handler.
     *
     * @param mergedTransitionId The id of the transition that was merged.
     * @param playingTransitionId The id of the transition the transition was merged into.
     */
    void logMerged(int mergedTransitionId, int playingTransitionId);

    /**
     * Adds an entry in the trace to log that a transition was aborted.
     *
     * @param transitionId The id of the transition that was aborted.
     */
    void logAborted(int transitionId);
}
