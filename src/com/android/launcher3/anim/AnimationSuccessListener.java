

package com.android.launcher3.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

import androidx.annotation.CallSuper;

/**
 * Extension of {@link AnimatorListenerAdapter} for listening for non-cancelled animations
 */
public abstract class AnimationSuccessListener extends AnimatorListenerAdapter {

    protected boolean mCancelled = false;

    @Override
    @CallSuper
    public void onAnimationCancel(Animator animation) {
        mCancelled = true;
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        if (!mCancelled) {
            onAnimationSuccess(animation);
        }
    }

    public abstract void onAnimationSuccess(Animator animator);

}
