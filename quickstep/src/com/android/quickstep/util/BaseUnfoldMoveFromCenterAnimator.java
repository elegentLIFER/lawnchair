
package com.android.quickstep.util;

import android.annotation.CallSuper;
import android.view.Surface.Rotation;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.MainThread;

import com.android.systemui.shared.animation.UnfoldMoveFromCenterAnimator;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener;
import com.android.systemui.unfold.dagger.UnfoldMain;
import com.android.systemui.unfold.updates.RotationChangeProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * Animation that moves launcher icons and widgets from center to the sides (final position)
 */
public abstract class BaseUnfoldMoveFromCenterAnimator implements TransitionProgressListener {

    private final UnfoldMoveFromCenterAnimator mMoveFromCenterAnimation;
    @UnfoldMain private final RotationChangeProvider mRotationChangeProvider;

    private final Map<ViewGroup, Boolean> mOriginalClipToPadding = new HashMap<>();
    private final Map<ViewGroup, Boolean> mOriginalClipChildren = new HashMap<>();

    private final UnfoldMoveFromCenterRotationListener mRotationListener =
            new UnfoldMoveFromCenterRotationListener();
    private boolean mAnimationInProgress = false;

    // Save the last transition progress so we can re-apply it in case we re-register the view for
    // the animation (by calling onPrepareViewsForAnimation)
    private Float mLastTransitionProgress = null;

    public BaseUnfoldMoveFromCenterAnimator(WindowManager windowManager,
            @UnfoldMain RotationChangeProvider rotationChangeProvider) {
        mMoveFromCenterAnimation = new UnfoldMoveFromCenterAnimator(windowManager,
                new LauncherViewsMoveFromCenterTranslationApplier());
        mRotationChangeProvider = rotationChangeProvider;
    }

    @CallSuper
    @Override
    public void onTransitionStarted() {
        mAnimationInProgress = true;
        mMoveFromCenterAnimation.updateDisplayProperties();
        onPrepareViewsForAnimation();
        mRotationChangeProvider.addCallback(mRotationListener);
    }

    @CallSuper
    @Override
    public void onTransitionProgress(float progress) {
        mMoveFromCenterAnimation.onTransitionProgress(progress);
        mLastTransitionProgress = progress;
    }

    @CallSuper
    @Override
    public void onTransitionFinished() {
        mLastTransitionProgress = null;
        mAnimationInProgress = false;
        mRotationChangeProvider.removeCallback(mRotationListener);
        mMoveFromCenterAnimation.onTransitionFinished();
        clearRegisteredViews();
    }

    /**
     * Re-prepares views for animation. This is useful in case views are re-bound while the
     * animation is in progress.
     */
    public void updateRegisteredViewsIfNeeded() {
        if (mAnimationInProgress) {
            clearRegisteredViews();
            onPrepareViewsForAnimation();
        }
    }

    private void clearRegisteredViews() {
        restoreClippings();
        mMoveFromCenterAnimation.clearRegisteredViews();

        mOriginalClipChildren.clear();
        mOriginalClipToPadding.clear();
    }

    @CallSuper
    protected void onPrepareViewsForAnimation() {
        if (mLastTransitionProgress != null) {
            mMoveFromCenterAnimation.onTransitionProgress(mLastTransitionProgress);
        }
    }

    protected void registerViewForAnimation(View view) {
        mMoveFromCenterAnimation.registerViewForAnimation(view);
    }

    /**
     * Sets clipToPadding for the view which then could be restored to the original value
     * using {@link BaseUnfoldMoveFromCenterAnimator#restoreClippings} method call
     * @param view view to set the property
     * @param clipToPadding value of the property
     */
    protected void setClipToPadding(ViewGroup view, boolean clipToPadding) {
        mOriginalClipToPadding.put(view, view.getClipToPadding());
        view.setClipToPadding(clipToPadding);
    }

    /**
     * Sets clipChildren for the view which then could be restored to the original value
     * using {@link BaseUnfoldMoveFromCenterAnimator#restoreClippings} method call
     * @param view view to set the property
     * @param clipChildren value of the property
     */
    protected void setClipChildren(ViewGroup view, boolean clipChildren) {
        mOriginalClipChildren.put(view, view.getClipChildren());
        view.setClipChildren(clipChildren);
    }

    /**
     * Restores original clip properties after their modifications
     */
    protected void restoreClippings() {
        mOriginalClipToPadding.forEach(ViewGroup::setClipToPadding);
        mOriginalClipChildren.forEach(ViewGroup::setClipChildren);
    }

    private class UnfoldMoveFromCenterRotationListener implements
            RotationChangeProvider.RotationListener {

        @MainThread
        @Override
        public void onRotationChanged(@Rotation int newRotation) {
            onRotationChangedInternal(newRotation);
        }

        @MainThread
        private void onRotationChangedInternal(@Rotation int newRotation) {
            mMoveFromCenterAnimation.updateDisplayProperties(newRotation);
            updateRegisteredViewsIfNeeded();
        }
    }
}
