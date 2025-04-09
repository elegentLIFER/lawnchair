

package com.android.quickstep.util

/** Timings for the app pair launch animation on tablets. */
class TabletAppPairLaunchTimings : AppPairLaunchTimings(), SplitAnimationTimings {
    override val STAGED_RECT_SLIDE_DURATION = 600
    override fun getDuration() = SplitAnimationTimings.TABLET_APP_PAIR_LAUNCH_DURATION
}
