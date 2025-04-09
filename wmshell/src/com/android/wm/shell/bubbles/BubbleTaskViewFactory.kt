

package com.android.wm.shell.bubbles

/** Factory for creating [BubbleTaskView]s. */
fun interface BubbleTaskViewFactory {
    /** Creates a new instance of [BubbleTaskView]. */
    fun create(): BubbleTaskView
}
