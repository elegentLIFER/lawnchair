
package com.android.quickstep.util;

import android.view.View;
import android.view.WindowManager;

import com.android.launcher3.CellLayout;
import com.android.launcher3.ShortcutAndWidgetContainer;
import com.android.launcher3.Workspace;
import com.android.launcher3.uioverrides.QuickstepLauncher;
import com.android.systemui.unfold.dagger.UnfoldMain;
import com.android.systemui.unfold.updates.RotationChangeProvider;

/**
 * Animation that moves launcher icons and widgets from center to the sides (final position)
 */
public class UnfoldMoveFromCenterWorkspaceAnimator extends BaseUnfoldMoveFromCenterAnimator {

    private final QuickstepLauncher mLauncher;

    public UnfoldMoveFromCenterWorkspaceAnimator(
            QuickstepLauncher launcher, WindowManager windowManager,
            @UnfoldMain RotationChangeProvider rotationChangeProvider) {
        super(windowManager, rotationChangeProvider);
        mLauncher = launcher;
    }

    @Override
    protected void onPrepareViewsForAnimation() {
        Workspace<?> workspace = mLauncher.getWorkspace();

        // App icons and widgets
        workspace
                .forEachVisiblePage(page -> {
                    final CellLayout cellLayout = (CellLayout) page;
                    ShortcutAndWidgetContainer itemsContainer = cellLayout
                            .getShortcutsAndWidgets();
                    setClipChildren(cellLayout, false);
                    setClipToPadding(cellLayout, false);

                    for (int i = 0; i < itemsContainer.getChildCount(); i++) {
                        View child = itemsContainer.getChildAt(i);
                        registerViewForAnimation(child);
                    }
                });

        setClipChildren(workspace, false);
        setClipToPadding(workspace, true);

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
