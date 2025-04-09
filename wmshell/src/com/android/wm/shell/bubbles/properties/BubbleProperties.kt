

package com.android.wm.shell.bubbles.properties

/**
 * An interface for exposing bubble properties via flags which can be controlled easily in tests.
 */
interface BubbleProperties {
    /**
     * Whether bubble bar is enabled.
     *
     * When this is `true`, depending on additional factors, such as screen size and taskbar state,
     * bubbles will be displayed in the bubble bar instead of floating.
     *
     * When this is `false`, bubbles will be floating.
     */
    val isBubbleBarEnabled: Boolean

    /** Refreshes the current value of [isBubbleBarEnabled]. */
    fun refresh()
}
