

package com.android.systemui.shared.system;

import android.app.AppGlobals;
import android.app.admin.DevicePolicyManager;

/**
 * Wrapper for {@link DevicePolicyManager}.
 */
public class DevicePolicyManagerWrapper {
    private static final DevicePolicyManagerWrapper sInstance = new DevicePolicyManagerWrapper();

    private static final DevicePolicyManager sDevicePolicyManager =
            AppGlobals.getInitialApplication().getSystemService(DevicePolicyManager.class);

    private DevicePolicyManagerWrapper() { }

    public static DevicePolicyManagerWrapper getInstance() {
        return sInstance;
    }

    /**
     * Returns whether the given package is allowed to run in Lock Task mode.
     */
    public boolean isLockTaskPermitted(String pkg) {
        return sDevicePolicyManager.isLockTaskPermitted(pkg);
    }
}
