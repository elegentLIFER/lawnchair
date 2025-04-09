
package com.android.launcher3.uioverrides.touchcontrollers;

import com.android.launcher3.LauncherState;
import com.android.launcher3.touch.SingleAxisSwipeDetector;
import com.android.launcher3.uioverrides.QuickstepLauncher;

public class TransposedQuickSwitchTouchController extends QuickSwitchTouchController {

    public TransposedQuickSwitchTouchController(QuickstepLauncher launcher) {
        super(launcher, SingleAxisSwipeDetector.VERTICAL);
    }

    @Override
    protected LauncherState getTargetState(LauncherState fromState, boolean isDragTowardPositive) {
        return super.getTargetState(fromState,
                isDragTowardPositive ^ mLauncher.getDeviceProfile().isSeascape());
    }

    @Override
    protected float initCurrentAnimation() {
        float multiplier = super.initCurrentAnimation();
        return mLauncher.getDeviceProfile().isSeascape() ? multiplier : -multiplier;
    }

    @Override
    protected float getShiftRange() {
        return mLauncher.getDeviceProfile().heightPx / 2f;
    }
}
