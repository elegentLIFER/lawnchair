
package com.android.wm.shell.back

import android.content.Context
import android.view.SurfaceControl
import android.window.BackEvent
import com.android.wm.shell.R
import com.android.wm.shell.RootTaskDisplayAreaOrganizer
import com.android.wm.shell.animation.Interpolators
import javax.inject.Inject
import kotlin.math.max

/** Class that defines cross-activity animation. */
class DefaultCrossActivityBackAnimation
@Inject
constructor(
    context: Context,
    background: BackAnimationBackground,
    rootTaskDisplayAreaOrganizer: RootTaskDisplayAreaOrganizer
) :
    CrossActivityBackAnimation(
        context,
        background,
        rootTaskDisplayAreaOrganizer,
        SurfaceControl.Transaction()
    ) {

    private val postCommitInterpolator = Interpolators.EMPHASIZED
    private val enteringStartOffset =
        context.resources.getDimension(R.dimen.cross_activity_back_entering_start_offset)
    override val allowEnteringYShift = true

    override fun preparePreCommitClosingRectMovement(swipeEdge: Int) {
        startClosingRect.set(backAnimRect)

        // scale closing target into the middle for rhs and to the right for lhs
        targetClosingRect.set(startClosingRect)
        targetClosingRect.scaleCentered(MAX_SCALE)
        if (swipeEdge != BackEvent.EDGE_RIGHT) {
            targetClosingRect.offset(
                    startClosingRect.right - targetClosingRect.right - displayBoundsMargin,
                    0f
            )
        }
    }

    override fun preparePreCommitEnteringRectMovement() {
        // the entering target starts 96dp to the left of the screen edge...
        startEnteringRect.set(startClosingRect)
        startEnteringRect.offset(-enteringStartOffset, 0f)
        // ...and gets scaled in sync with the closing target
        targetEnteringRect.set(startEnteringRect)
        targetEnteringRect.scaleCentered(MAX_SCALE)
    }

    override fun getPostCommitAnimationDuration() = POST_COMMIT_DURATION

    override fun onGestureCommitted(velocity: Float) {
        // We enter phase 2 of the animation, the starting coordinates for phase 2 are the current
        // coordinate of the gesture driven phase. Let's update the start and target rects and kick
        // off the animator in the superclass
        startClosingRect.set(currentClosingRect)
        startEnteringRect.set(currentEnteringRect)
        targetEnteringRect.set(backAnimRect)
        targetClosingRect.set(backAnimRect)
        targetClosingRect.offset(currentClosingRect.left + enteringStartOffset, 0f)
        super.onGestureCommitted(velocity)
    }

    override fun onPostCommitProgress(linearProgress: Float) {
        super.onPostCommitProgress(linearProgress)
        val closingAlpha = max(1f - linearProgress * 5, 0f)
        val progress = postCommitInterpolator.getInterpolation(linearProgress)
        currentClosingRect.setInterpolatedRectF(startClosingRect, targetClosingRect, progress)
        applyTransform(
            closingTarget?.leash,
            currentClosingRect,
            closingAlpha,
            flingMode = FlingMode.FLING_BOUNCE
        )
        currentEnteringRect.setInterpolatedRectF(startEnteringRect, targetEnteringRect, progress)
        applyTransform(
            enteringTarget?.leash,
            currentEnteringRect,
            1f,
            flingMode = FlingMode.FLING_BOUNCE
        )
        applyTransaction()
    }


    companion object {
        private const val POST_COMMIT_DURATION = 450L
    }
}
