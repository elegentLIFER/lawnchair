

package com.android.wm.shell.bubbles

import java.util.function.Consumer

/** Defines callbacks from [BubbleStackView] to its manager. */
interface BubbleStackViewManager {

    /** Notifies that all bubbles animated out. */
    fun onAllBubblesAnimatedOut()

    /** Notifies whether backpress should be intercepted. */
    fun updateWindowFlagsForBackpress(interceptBack: Boolean)

    /**
     * Checks the current expansion state of the notification panel, and invokes [callback] with the
     * result.
     */
    fun checkNotificationPanelExpandedState(callback: Consumer<Boolean>)

    /** Requests to hide the current input method. */
    fun hideCurrentInputMethod()

    companion object {

        @JvmStatic
        fun fromBubbleController(controller: BubbleController) = object : BubbleStackViewManager {
            override fun onAllBubblesAnimatedOut() {
                controller.onAllBubblesAnimatedOut()
            }

            override fun updateWindowFlagsForBackpress(interceptBack: Boolean) {
                controller.updateWindowFlagsForBackpress(interceptBack)
            }

            override fun checkNotificationPanelExpandedState(callback: Consumer<Boolean>) {
                controller.isNotificationPanelExpanded(callback)
            }

            override fun hideCurrentInputMethod() {
                controller.hideCurrentInputMethod()
            }
        }
    }
}
