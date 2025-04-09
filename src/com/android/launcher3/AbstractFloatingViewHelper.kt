

package com.android.launcher3

import com.android.launcher3.AbstractFloatingView.FloatingViewType
import com.android.launcher3.views.ActivityContext

/**
 * Helper class for manaing AbstractFloatingViews which shows a floating UI on top of the launcher
 * UI.
 */
class AbstractFloatingViewHelper {
    fun closeOpenViews(activity: ActivityContext, animate: Boolean, @FloatingViewType type: Int) {
        val dragLayer = activity.getDragLayer()
        // Iterate in reverse order. AbstractFloatingView is added later to the dragLayer,
        // and will be one of the last views.
        for (i in dragLayer.getChildCount() - 1 downTo 0) {
            val child = dragLayer.getChildAt(i)
            if (child is AbstractFloatingView && child.isOfType(type)) {
                child.close(animate)
            }
        }
    }
}
