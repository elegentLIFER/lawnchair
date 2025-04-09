

package com.android.systemui.shared.system.smartspace;

import com.android.systemui.shared.system.smartspace.ILauncherUnlockAnimationController;
import com.android.systemui.shared.system.smartspace.SmartspaceState;

// System UI unlock controller. Launcher will provide a LauncherUnlockAnimationController to this
// controller, which System UI will use to control the unlock animation within the Launcher window.
interface ISysuiUnlockAnimationController {
    // Provides an implementation of the LauncherUnlockAnimationController to System UI, so that
    // SysUI can use it to control the unlock animation in the launcher window.
    oneway void setLauncherUnlockController(
        String activityClass, ILauncherUnlockAnimationController callback);

    // Called by Launcher whenever anything happens to change the state of its smartspace. System UI
    // proactively saves this and uses it to perform the unlock animation without needing to make a
    // blocking query to Launcher asking about the smartspace state.
    oneway void onLauncherSmartspaceStateUpdated(in SmartspaceState state);
}
