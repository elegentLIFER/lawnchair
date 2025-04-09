
package com.android.systemui.unfold

import androidx.annotation.FloatRange
import com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
import com.android.systemui.unfold.util.CallbackController

/**
 * Interface that allows to receive unfold transition progress updates.
 *
 * It can be used to update view properties based on the current animation progress.
 *
 * onTransitionProgress callback could be called on each frame.
 *
 * Use [createUnfoldSharedComponent] to create instances of this interface when dagger is not
 * available.
 */
interface UnfoldTransitionProgressProvider : CallbackController<TransitionProgressListener> {

    fun destroy()

    interface TransitionProgressListener {
        /** Called when transition is started */
        fun onTransitionStarted() {}

        /**
         * Called whenever transition progress is updated, [progress] is a value of the animation
         * where 0 is fully folded, 1 is fully unfolded
         */
        fun onTransitionProgress(@FloatRange(from = 0.0, to = 1.0) progress: Float) {}

        /**
         * Called when the progress provider determined that the transition is about to finish soon.
         *
         * For example, in [PhysicsBasedUnfoldTransitionProgressProvider] this could happen when the
         * animation is not tied to the hinge angle anymore and it is about to run fixed animation.
         */
        fun onTransitionFinishing() {}

        /** Called when transition is completely finished */
        fun onTransitionFinished() {}
    }
}
