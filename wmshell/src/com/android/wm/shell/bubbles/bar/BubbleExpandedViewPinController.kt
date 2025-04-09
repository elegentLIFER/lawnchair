

package com.android.wm.shell.bubbles.bar

import android.content.Context
import android.graphics.Point
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.updateLayoutParams
import com.android.wm.shell.R
import com.android.wm.shell.bubbles.BubblePositioner
import com.android.wm.shell.common.bubbles.BaseBubblePinController
import com.android.wm.shell.common.bubbles.BubbleBarLocation

/**
 * Controller to manage pinning bubble bar to left or right when dragging starts from the bubble bar
 * expanded view
 */
class BubbleExpandedViewPinController(
    private val context: Context,
    private val container: FrameLayout,
    private val positioner: BubblePositioner
) : BaseBubblePinController({ positioner.availableRect.let { Point(it.width(), it.height()) } }) {

    private var dropTargetView: View? = null
    private val tempRect: Rect by lazy(LazyThreadSafetyMode.NONE) { Rect() }

    private val exclRectWidth: Float by lazy {
        context.resources.getDimension(R.dimen.bubble_bar_dismiss_zone_width)
    }

    private val exclRectHeight: Float by lazy {
        context.resources.getDimension(R.dimen.bubble_bar_dismiss_zone_height)
    }

    override fun getExclusionRectWidth(): Float {
        return exclRectWidth
    }

    override fun getExclusionRectHeight(): Float {
        return exclRectHeight
    }

    override fun createDropTargetView(): View {
        return LayoutInflater.from(context)
            .inflate(R.layout.bubble_bar_drop_target, container, false /* attachToRoot */)
            .also { view: View ->
                dropTargetView = view
                // Add at index 0 to ensure it does not cover the bubble
                container.addView(view, 0)
            }
    }

    override fun getDropTargetView(): View? {
        return dropTargetView
    }

    override fun removeDropTargetView(view: View) {
        container.removeView(view)
        dropTargetView = null
    }

    override fun updateLocation(location: BubbleBarLocation) {
        val view = dropTargetView ?: return
        positioner.getBubbleBarExpandedViewBounds(
            location.isOnLeft(view.isLayoutRtl),
            false /* isOverflowExpanded */,
            tempRect
        )
        view.updateLayoutParams<FrameLayout.LayoutParams> {
            width = tempRect.width()
            height = tempRect.height()
        }
        view.x = tempRect.left.toFloat()
        view.y = tempRect.top.toFloat()
    }
}
