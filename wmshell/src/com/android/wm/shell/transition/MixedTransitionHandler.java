

package com.android.wm.shell.transition;

/**
 * Interface for a {@link Transitions.TransitionHandler} that can take the subset of transitions
 * that it handles and further decompose those transitions into sub-transitions which can be
 * independently delegated to separate handlers.
 */
public interface MixedTransitionHandler extends Transitions.TransitionHandler {

    // TODO(b/335685449) this currently exists purely as a marker interface for use in form-factor
    // specific/sysui dagger modules. Going forward, we should define this in a meaningful
    // way so as to provide a clear basis for expectations/behaviours associated with mixed
    // transitions and their default handlers.

}
