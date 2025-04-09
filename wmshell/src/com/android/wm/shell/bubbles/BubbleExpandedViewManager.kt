

package com.android.wm.shell.bubbles

/** Manager interface for bubble expanded views. */
interface BubbleExpandedViewManager {

    val overflowBubbles: List<Bubble>
    fun setOverflowListener(listener: BubbleData.Listener)
    fun collapseStack()
    fun updateWindowFlagsForBackpress(intercept: Boolean)
    fun promoteBubbleFromOverflow(bubble: Bubble)
    fun removeBubble(key: String, reason: Int)
    fun dismissBubble(bubble: Bubble, reason: Int)
    fun setAppBubbleTaskId(key: String, taskId: Int)
    fun isStackExpanded(): Boolean
    fun isShowingAsBubbleBar(): Boolean
    fun hideCurrentInputMethod()

    companion object {
        /**
         * Convenience function for creating a [BubbleExpandedViewManager] that delegates to the
         * given `controller`.
         */
        @JvmStatic
        fun fromBubbleController(controller: BubbleController): BubbleExpandedViewManager {
            return object : BubbleExpandedViewManager {

                override val overflowBubbles: List<Bubble>
                    get() = controller.overflowBubbles

                override fun setOverflowListener(listener: BubbleData.Listener) {
                    controller.setOverflowListener(listener)
                }

                override fun collapseStack() {
                    controller.collapseStack()
                }

                override fun updateWindowFlagsForBackpress(intercept: Boolean) {
                    controller.updateWindowFlagsForBackpress(intercept)
                }

                override fun promoteBubbleFromOverflow(bubble: Bubble) {
                    controller.promoteBubbleFromOverflow(bubble)
                }

                override fun removeBubble(key: String, reason: Int) {
                    controller.removeBubble(key, reason)
                }

                override fun dismissBubble(bubble: Bubble, reason: Int) {
                    controller.dismissBubble(bubble, reason)
                }

                override fun setAppBubbleTaskId(key: String, taskId: Int) {
                    controller.setAppBubbleTaskId(key, taskId)
                }

                override fun isStackExpanded(): Boolean = controller.isStackExpanded

                override fun isShowingAsBubbleBar(): Boolean = controller.isShowingAsBubbleBar

                override fun hideCurrentInputMethod() {
                    controller.hideCurrentInputMethod()
                }
            }
        }
    }
}
