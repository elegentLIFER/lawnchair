

package com.android.wm.shell.bubbles.animation

/** Utils related to the fling to dismiss animation. */
object FlingToDismissUtils {

    /** The target width surrounding the dismiss target on a small width screen, e.g. phone. */
    private const val FLING_TO_DISMISS_TARGET_WIDTH_SMALL = 3f
    /**
     * The target width surrounding the dismiss target on a medium width screen, e.g. tablet in
     * portrait.
     */
    private const val FLING_TO_DISMISS_TARGET_WIDTH_MEDIUM = 4.5f
    /**
     * The target width surrounding the dismiss target on a large width screen, e.g. tablet in
     * landscape.
     */
    private const val FLING_TO_DISMISS_TARGET_WIDTH_LARGE = 6f

    /** Returns the dismiss target width for the specified [screenWidthPx]. */
    @JvmStatic
    fun getFlingToDismissTargetWidth(screenWidthPx: Int) = when {
        screenWidthPx >= 2000 -> FLING_TO_DISMISS_TARGET_WIDTH_LARGE
        screenWidthPx >= 1500 -> FLING_TO_DISMISS_TARGET_WIDTH_MEDIUM
        else -> FLING_TO_DISMISS_TARGET_WIDTH_SMALL
    }
}
