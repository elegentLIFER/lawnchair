

package com.android.quickstep.util;

/**
 * Timings for the OverviewSplitSelect > confirmed animation on phones.
 */
public class PhoneSplitToConfirmTimings
        extends SplitToConfirmTimings implements SplitAnimationTimings {
    public int getPlaceholderFadeInStart() { return 0; }
    public int getPlaceholderFadeInEnd() { return 133; }
    public int getPlaceholderIconFadeInStart() { return 50; }
    public int getPlaceholderIconFadeInEnd() { return 133; }
    public int getStagedRectSlideStart() { return 0; }
    public int getStagedRectSlideEnd() { return 333; }
    public int getBackingScrimFadeInStart() { return 0; }
    public int getBackingScrimFadeInEnd() { return 266; }

    public int getDuration() { return PHONE_CONFIRM_DURATION; }
}
