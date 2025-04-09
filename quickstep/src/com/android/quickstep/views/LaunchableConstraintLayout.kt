

package com.android.quickstep.views

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.systemui.animation.LaunchableView
import com.android.systemui.animation.LaunchableViewDelegate

/** A [ConstraintLayout] that also implements [LaunchableView]. */
open class LaunchableConstraintLayout : ConstraintLayout, LaunchableView {
    private val delegate =
        LaunchableViewDelegate(
            this,
            superSetVisibility = { super.setVisibility(it) },
        )

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
    ) : super(context, attrs, defStyleAttr)

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int,
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun setShouldBlockVisibilityChanges(block: Boolean) {
        delegate.setShouldBlockVisibilityChanges(block)
    }

    override fun setVisibility(visibility: Int) {
        // Note that super.setVisibility() is passed to the delegate upon creation and called by it.
        // This method is just a passthrough if no animation is in progress, whereas otherwise it
        // caches the passed value and restores it at the end of the animation.
        delegate.setVisibility(visibility)
    }
}
