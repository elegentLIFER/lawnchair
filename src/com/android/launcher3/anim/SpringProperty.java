
package com.android.launcher3.anim;

import androidx.dynamicanimation.animation.SpringForce;

/**
 * Utility class to store configurations for spring animation
 */
public class SpringProperty {

    public static final SpringProperty DEFAULT = new SpringProperty();

    // Play spring when the animation is going towards the end
    public static final int FLAG_CAN_SPRING_ON_END = 1 << 0;
    // Play spring when animation is going towards the start (in reverse direction)
    public static final int FLAG_CAN_SPRING_ON_START = 1 << 1;

    public final int flags;

    float mDampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY;
    float mStiffness = SpringForce.STIFFNESS_MEDIUM;

    public SpringProperty() {
        this(0);
    }

    public SpringProperty(int flags) {
        this.flags = flags;
    }

    public SpringProperty setDampingRatio(float dampingRatio) {
        mDampingRatio = dampingRatio;
        return this;
    }

    public SpringProperty setStiffness(float stiffness) {
        mStiffness = stiffness;
        return this;
    }
}
