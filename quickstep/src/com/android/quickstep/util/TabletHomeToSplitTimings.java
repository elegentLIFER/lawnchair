

package com.android.quickstep.util;

import static com.android.app.animation.Interpolators.LINEAR;

import android.view.animation.Interpolator;

/**
 * Timings for the Home > OverviewSplitSelect animation on tablets.
 */
public class TabletHomeToSplitTimings
        extends TabletOverviewToSplitTimings implements SplitAnimationTimings {
    @Override
    public Interpolator getStagedRectXInterpolator() { return LINEAR; }
    @Override
    public Interpolator getStagedRectScaleXInterpolator() { return LINEAR; }
    @Override
    public Interpolator getStagedRectScaleYInterpolator() { return LINEAR; }

    public int getScrimFadeInStart() { return 0; }
    public int getScrimFadeInEnd() { return 167; }

    public float getScrimFadeInStartOffset() {
        return (float) getScrimFadeInStart() / getDuration();
    }
    public float getScrimFadeInEndOffset() {
        return (float) getScrimFadeInEnd() / getDuration();
    }
}
