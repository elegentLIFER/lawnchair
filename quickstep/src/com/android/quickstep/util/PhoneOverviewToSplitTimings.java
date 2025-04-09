

package com.android.quickstep.util;

import static com.android.app.animation.Interpolators.EMPHASIZED;

import android.view.animation.Interpolator;

/**
 * Timings for the Overview > OverviewSplitSelect animation on phones.
 */
public class PhoneOverviewToSplitTimings
        extends OverviewToSplitTimings implements SplitAnimationTimings {
    public int getPlaceholderFadeInStart() { return 0; }
    public int getPlaceholderFadeInEnd() { return 133; }
    public int getPlaceholderIconFadeInStart() { return 83; }
    public int getPlaceholderIconFadeInEnd() { return 167; }
    public int getStagedRectSlideStart() { return 0; }
    public int getStagedRectSlideEnd() { return 333; }
    public int getGridSlideStart() { return 100; }
    public int getGridSlideStagger() { return 0; }
    public int getGridSlideDuration() { return 417; }

    public int getDuration() { return PHONE_ENTER_DURATION; }
    public Interpolator getStagedRectXInterpolator() { return EMPHASIZED; }
    public Interpolator getStagedRectYInterpolator() { return EMPHASIZED; }
    public Interpolator getStagedRectScaleXInterpolator() { return EMPHASIZED; }
    public Interpolator getStagedRectScaleYInterpolator() { return EMPHASIZED; }
}
