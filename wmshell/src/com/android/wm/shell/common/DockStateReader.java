

package com.android.wm.shell.common;

import static android.content.Intent.EXTRA_DOCK_STATE;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.android.wm.shell.dagger.WMSingleton;

import javax.inject.Inject;

/**
 * Provides information about the docked state of the device.
 */
@WMSingleton
public class DockStateReader {

    private static final IntentFilter DOCK_INTENT_FILTER = new IntentFilter(
            Intent.ACTION_DOCK_EVENT);

    private final Context mContext;

    @Inject
    public DockStateReader(Context context) {
        mContext = context;
    }

    /**
     * @return True if the device is docked and false otherwise.
     */
    public boolean isDocked() {
        Intent dockStatus = mContext.registerReceiver(/* receiver */ null, DOCK_INTENT_FILTER);
        if (dockStatus != null) {
            int dockState = dockStatus.getIntExtra(EXTRA_DOCK_STATE,
                    Intent.EXTRA_DOCK_STATE_UNDOCKED);
            return dockState != Intent.EXTRA_DOCK_STATE_UNDOCKED;
        }
        return false;
    }
}
