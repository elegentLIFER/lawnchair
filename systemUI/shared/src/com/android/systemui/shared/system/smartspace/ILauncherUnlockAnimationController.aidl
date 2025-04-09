

package com.android.systemui.shared.system.smartspace;

import android.graphics.Rect;
import com.android.systemui.shared.system.smartspace.SmartspaceState;

// Methods for System UI to interface with Launcher to perform the unlock animation.
interface ILauncherUnlockAnimationController {
    // Prepares Launcher for the unlock animation by setting scale/alpha/etc. to their starting
    // values.
    void prepareForUnlock(boolean animateSmartspace, in Rect lockscreenSmartspaceBounds,
        int selectedPage);

    // Set the unlock percentage. This is used when System UI is controlling each frame of the
    // unlock animation, such as during a swipe to unlock touch gesture. Will not apply this change
    // if the unlock amount is animating unless forceIfAnimating is true.
    oneway void setUnlockAmount(float amount, boolean forceIfAnimating);

    // Play a full unlock animation from 0f to 1f. This is used when System UI is unlocking from a
    // single action, such as biometric auth, and doesn't need to control individual frames.
    oneway void playUnlockAnimation(boolean unlocked, long duration, long startDelay);

    // Set the selected page on Launcher's smartspace.
    oneway void setSmartspaceSelectedPage(int selectedPage);

    // Set the visibility of Launcher's smartspace.
    void setSmartspaceVisibility(int visibility);

    // Tell SystemUI the smartspace's current state. Launcher code should call this whenever the
    // smartspace state may have changed.
    oneway void dispatchSmartspaceStateToSysui();
}
