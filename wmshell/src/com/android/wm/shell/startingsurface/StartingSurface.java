

package com.android.wm.shell.startingsurface;

import android.app.TaskInfo;
import android.graphics.Color;
/**
 * Interface to engage starting window feature.
 */
public interface StartingSurface {
    /**
     * Returns the background color for a starting window if existing.
     */
    default int getBackgroundColor(TaskInfo taskInfo) {
        return Color.BLACK;
    }

    /** Set the proxy to communicate with SysUi side components. */
    void setSysuiProxy(SysuiProxy proxy);

    /** Callback to tell SysUi components execute some methods. */
    interface SysuiProxy {
        void requestTopUi(boolean requestTopUi, String componentTag);
    }
}
