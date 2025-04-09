

package com.android.launcher3.util;

import static com.android.launcher3.LauncherAnimUtils.VIEW_ALPHA;

import android.view.View;

import com.android.launcher3.anim.AlphaUpdateListener;

/**
 * Utility class to handle separating a single value as a factor of multiple values
 */
public class MultiValueAlpha extends MultiPropertyFactory<View> {

    private static final FloatBiFunction ALPHA_AGGREGATOR = (a, b) -> a * b;

    // Whether we should change from INVISIBLE to VISIBLE and vice versa at low alpha values.
    private boolean mUpdateVisibility;

    private final int mHiddenVisibility;

    public MultiValueAlpha(View view, int size) {
        this(view, size, View.INVISIBLE);
    }

    public MultiValueAlpha(View view, int size, int hiddenVisibility) {
        super(view, VIEW_ALPHA, size, ALPHA_AGGREGATOR, 1f);
        this.mHiddenVisibility = hiddenVisibility;
    }

    /** Sets whether we should update between INVISIBLE and VISIBLE based on alpha. */
    public void setUpdateVisibility(boolean updateVisibility) {
        mUpdateVisibility = updateVisibility;
    }

    @Override
    protected void apply(float value) {
        super.apply(value);
        if (mUpdateVisibility) {
            AlphaUpdateListener.updateVisibility(mTarget, mHiddenVisibility);
        }
    }
}
