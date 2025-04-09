

package com.android.wm.shell.draganddrop;

/**
 * Interface that is exposed to remote callers to manipulate drag and drop.
 */
interface IDragAndDrop {
    /**
     * Returns whether the shell drop target is showing and will handle a drag/drop.
     */
    boolean isReadyToHandleDrag() = 1;
}
// Last id = 1
