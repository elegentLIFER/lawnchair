

package com.android.quickstep.util;

/**
 * Timings for the OverviewSplitSelect > confirmed animation on tablets.
 */
public class TabletSplitToConfirmTimings
        extends SplitToConfirmTimings implements SplitAnimationTimings {
    public int getPlaceholderFadeInStart() { return 0; }
    public int getPlaceholderFadeInEnd() { return 133; }
    public int getPlaceholderIconFadeInStart() { return 167; }
    public int getPlaceholderIconFadeInEnd() { return 250; }
    public int getStagedRectSlideStart() { return 0; }
    public int getStagedRectSlideEnd() { return 500; }
    public int getBackingScrimFadeInStart() { return 0; }
    public int getBackingScrimFadeInEnd() { return 400; }

    public int getDuration() { return TABLET_CONFIRM_DURATION; }
}
