

package com.android.wm.shell.startingsurface;

import com.android.wm.shell.startingsurface.IStartingWindowListener;

/**
 * Interface that is exposed to remote callers to manipulate starting windows.
 */
interface IStartingWindow {
    /**
     * Sets listener to get task launching callbacks.
     */
    oneway void setStartingWindowListener(IStartingWindowListener listener) = 43;
}
