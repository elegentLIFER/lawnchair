
package com.android.launcher3.anim;

import android.content.Context;

import androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener;
import androidx.dynamicanimation.animation.FlingAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;

import com.android.launcher3.R;
import com.android.launcher3.util.DynamicResource;
import com.android.systemui.plugins.ResourceProvider;

/**
 * Given a property to animate and a target value and starting velocity, first apply friction to
 * the fling until we pass the target, then apply a spring force to pull towards the target.
 */
public class FlingSpringAnim {

    private final FlingAnimation mFlingAnim;
    private SpringAnimation mSpringAnim;
    private final boolean mSkipFlingAnim;

    private float mTargetPosition;

    public <K> FlingSpringAnim(K object, Context context, FloatPropertyCompat<K> property,
            float startPosition, float targetPosition, float startVelocityPxPerS,
            float minVisChange, float minValue, float maxValue, float damping, float stiffness,
            OnAnimationEndListener onEndListener) {
        ResourceProvider rp = DynamicResource.provider(context);
        float friction = rp.getFloat(R.dimen.swipe_up_rect_xy_fling_friction);

        mFlingAnim = new FlingAnimation(object, property)
                .setFriction(friction)
                // Have the spring pull towards the target if we've slowed down too much before
                // reaching it.
                .setMinimumVisibleChange(minVisChange)
                .setStartVelocity(startVelocityPxPerS)
                .setMinValue(minValue)
                .setMaxValue(maxValue);
        mTargetPosition = targetPosition;

        // We are already past the fling target, so skip it to avoid losing a frame of the spring.
        mSkipFlingAnim = startPosition <= minValue && startVelocityPxPerS < 0
                || startPosition >= maxValue && startVelocityPxPerS > 0;

        mFlingAnim.addEndListener(((animation, canceled, value, velocity) -> {
            mSpringAnim = new SpringAnimation(object, property)
                    .setStartValue(value)
                    .setStartVelocity(velocity)
                    .setSpring(new SpringForce(mTargetPosition)
                            .setStiffness(stiffness)
                            .setDampingRatio(damping));
            mSpringAnim.addEndListener(onEndListener);
            mSpringAnim.animateToFinalPosition(mTargetPosition);
        }));
    }

    public float getTargetPosition() {
        return mTargetPosition;
    }

    public void updatePosition(float startPosition, float targetPosition) {
        mFlingAnim.setMinValue(Math.min(startPosition, targetPosition))
                .setMaxValue(Math.max(startPosition, targetPosition));
        mTargetPosition = targetPosition;
        if (mSpringAnim != null) {
            mSpringAnim.animateToFinalPosition(mTargetPosition);
        }
    }

    public void start() {
        mFlingAnim.start();
        if (mSkipFlingAnim) {
            mFlingAnim.cancel();
        }
    }

    public void end() {
        mFlingAnim.cancel();
        if (mSpringAnim.canSkipToEnd()) {
            mSpringAnim.skipToEnd();
        }
    }
}
