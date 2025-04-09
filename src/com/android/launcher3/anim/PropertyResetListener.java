

package com.android.launcher3.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.util.Property;

/**
 * An AnimatorListener that sets the given property to the given value at the end of the animation.
 */
public class PropertyResetListener<T, V> extends AnimatorListenerAdapter {

    private Property<T, V> mPropertyToReset;
    private V mResetToValue;

    public PropertyResetListener(Property<T, V> propertyToReset, V resetToValue) {
        mPropertyToReset = propertyToReset;
        mResetToValue = resetToValue;
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        mPropertyToReset.set((T) ((ObjectAnimator) animation).getTarget(), mResetToValue);
    }
}
