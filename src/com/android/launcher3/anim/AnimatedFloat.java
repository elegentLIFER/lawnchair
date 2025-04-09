
package com.android.launcher3.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.util.FloatProperty;

import java.util.function.Consumer;

/**
 * A mutable float which allows animating the value
 */
public class AnimatedFloat {

    public static final FloatProperty<AnimatedFloat> VALUE =
            new FloatProperty<AnimatedFloat>("value") {
                @Override
                public void setValue(AnimatedFloat obj, float v) {
                    obj.updateValue(v);
                }

                @Override
                public Float get(AnimatedFloat obj) {
                    return obj.value;
                }
            };

    private static final Consumer<Float> NO_OP = t -> { };

    private final Consumer<Float> mUpdateCallback;
    private ObjectAnimator mValueAnimator;
    // Only non-null when an animation is playing to this value.
    private Float mEndValue;

    public float value;

    public AnimatedFloat() {
        this(NO_OP);
    }

    public AnimatedFloat(Runnable updateCallback) {
        this(v -> updateCallback.run());
    }

    public AnimatedFloat(Consumer<Float> updateCallback) {
        mUpdateCallback = updateCallback;
    }

    public AnimatedFloat(Runnable updateCallback, float initialValue) {
        this(updateCallback);
        value = initialValue;
    }

    public AnimatedFloat(Consumer<Float> updateCallback, float initialValue) {
        this(updateCallback);
        value = initialValue;
    }

    /**
     * Returns an animation from the current value to the given value.
     */
    public ObjectAnimator animateToValue(float end) {
        return animateToValue(value, end);
    }

    /**
     * Returns an animation from the given start value to the given end value.
     */
    public ObjectAnimator animateToValue(float start, float end) {
        cancelAnimation();
        mValueAnimator = ObjectAnimator.ofFloat(this, VALUE, start, end);
        mValueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animator) {
                if (mValueAnimator == animator) {
                    mEndValue = end;
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (mValueAnimator == animator) {
                    mValueAnimator = null;
                    mEndValue = null;
                }
            }
        });
        return mValueAnimator;
    }

    /**
     * Changes the value and calls the callback.
     * Note that the value can be directly accessed as well to avoid notifying the callback.
     */
    public void updateValue(float v) {
        if (Float.compare(v, value) != 0) {
            value = v;
            mUpdateCallback.accept(value);
        }
    }

    /**
     * Cancels the animation.
     */
    public void cancelAnimation() {
        if (mValueAnimator != null) {
            mValueAnimator.cancel();
            // Clears the property values, so further ObjectAnimator#setCurrentFraction from e.g.
            // AnimatorPlaybackController calls would do nothing. The null check is necessary to
            // avoid mValueAnimator being set to null in onAnimationEnd.
            if (mValueAnimator != null) {
                mValueAnimator.setValues();
                mValueAnimator = null;
            }
        }
    }

    /**
     * Ends the animation.
     */
    public void finishAnimation() {
        if (mValueAnimator != null && mValueAnimator.isRunning()) {
            mValueAnimator.end();
        }
    }

    public boolean isAnimating() {
        return mValueAnimator != null;
    }

    /**
     * Returns whether we are currently animating, and the animation's end value matches the given.
     */
    public boolean isAnimatingToValue(float endValue) {
        return isAnimating() && mEndValue != null && mEndValue == endValue;
    }

    /**
     * Returns the remaining time of the existing animation (if any).
     */
    public long getRemainingTime() {
        return isAnimating() && mValueAnimator.isRunning()
                ? Math.max(0, mValueAnimator.getDuration() - mValueAnimator.getCurrentPlayTime())
                : 0;
    }

    /**
     * Returns whether we are currently not animating, and the animation's value matches the given.
     */
    public boolean isSettledOnValue(float endValue) {
        return !isAnimating() && value == endValue;
    }
}
