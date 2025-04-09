

package com.android.wm.shell.pip.phone;

/**
 * A generic interface for a touch gesture.
 */
public abstract class PipTouchGesture {

    /**
     * Handle the touch down.
     */
    public void onDown(PipTouchState touchState) {}

    /**
     * Handle the touch move, and return whether the event was consumed.
     */
    public boolean onMove(PipTouchState touchState) {
        return false;
    }

    /**
     * Handle the touch up, and return whether the gesture was consumed.
     */
    public boolean onUp(PipTouchState touchState) {
        return false;
    }

    /**
     * Cleans up the high performance hint session if needed.
     */
    public void cleanUpHighPerfSessionMaybe() {}
}
