

package com.android.quickstep.util

/** Timings for the app pair launch animation on phones. */
class PhoneAppPairLaunchTimings : AppPairLaunchTimings(), SplitAnimationTimings {
    override val STAGED_RECT_SLIDE_DURATION = 500
    override fun getDuration() = SplitAnimationTimings.PHONE_APP_PAIR_LAUNCH_DURATION
}
