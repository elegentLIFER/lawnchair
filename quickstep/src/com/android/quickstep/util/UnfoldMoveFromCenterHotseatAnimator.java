
package com.android.quickstep.util;

import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.android.launcher3.Hotseat;
import com.android.launcher3.uioverrides.QuickstepLauncher;
import com.android.systemui.unfold.dagger.UnfoldMain;
import com.android.systemui.unfold.updates.RotationChangeProvider;

/**
 * Animation that moves hotseat icons from center to the sides (final position)
 */
public class UnfoldMoveFromCenterHotseatAnimator extends BaseUnfoldMoveFromCenterAnimator {

    private final QuickstepLauncher mLauncher;

    public UnfoldMoveFromCenterHotseatAnimator(
            QuickstepLauncher launcher, WindowManager windowManager,
            @UnfoldMain RotationChangeProvider rotationChangeProvider) {
        super(windowManager, rotationChangeProvider);
        mLauncher = launcher;
    }

    @Override
    protected void onPrepareViewsForAnimation() {
        Hotseat hotseat = mLauncher.getHotseat();

        ViewGroup hotseatIcons = hotseat.getShortcutsAndWidgets();
        setClipChildren(hotseat, false);
        setClipToPadding(hotseat, false);

        for (int i = 0; i < hotseatIcons.getChildCount(); i++) {
            View child = hotseatIcons.getChildAt(i);
            registerViewForAnimation(child);
        }

        super.onPrepareViewsForAnimation();
    }

    @Override
    public void onTransitionFinished() {
        restoreClippings();
        super.onTransitionFinished();
    }

    @Override
    public void onTransitionFinishing() {

    }
}
