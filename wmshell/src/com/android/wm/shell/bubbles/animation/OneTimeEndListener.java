

package com.android.wm.shell.bubbles.animation;

import androidx.dynamicanimation.animation.DynamicAnimation;

/**
 * End listener that removes itself from its animation when called for the first time. Useful since
 * anonymous OnAnimationEndListener instances can't pass themselves to
 * {@link DynamicAnimation#removeEndListener}, but can call through to this superclass
 * implementation.
 */
public class OneTimeEndListener implements DynamicAnimation.OnAnimationEndListener {

    @Override
    public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value,
            float velocity) {
        animation.removeEndListener(this);
    }
}
