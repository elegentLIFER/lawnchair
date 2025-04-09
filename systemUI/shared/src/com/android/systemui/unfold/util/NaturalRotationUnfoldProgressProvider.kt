
package com.android.systemui.unfold.util

import android.content.Context
import android.view.Surface
import com.android.systemui.unfold.UnfoldTransitionProgressProvider
import com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
import com.android.systemui.unfold.updates.RotationChangeProvider
import com.android.systemui.unfold.updates.RotationChangeProvider.RotationListener

/**
 * [UnfoldTransitionProgressProvider] that emits transition progress only when the display has
 * default rotation or 180 degrees opposite rotation (ROTATION_0 or ROTATION_180). It could be
 * helpful to run the animation only when the display's rotation is perpendicular to the fold.
 */
class NaturalRotationUnfoldProgressProvider(
    private val context: Context,
    private val rotationChangeProvider: RotationChangeProvider,
    unfoldTransitionProgressProvider: UnfoldTransitionProgressProvider
) : UnfoldTransitionProgressProvider {

    private val scopedUnfoldTransitionProgressProvider =
        ScopedUnfoldTransitionProgressProvider(unfoldTransitionProgressProvider)

    private var isNaturalRotation: Boolean = false

    fun init() {
        rotationChangeProvider.addCallback(rotationListener)
        context.display?.rotation?.let { rotationListener.onRotationChanged(it) }
    }

    private val rotationListener = RotationListener { rotation ->
        val isNewRotationNatural =
            rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180

        if (isNaturalRotation != isNewRotationNatural) {
            isNaturalRotation = isNewRotationNatural
            scopedUnfoldTransitionProgressProvider.setReadyToHandleTransition(isNewRotationNatural)
        }
    }

    override fun destroy() {
        rotationChangeProvider.removeCallback(rotationListener)
        scopedUnfoldTransitionProgressProvider.destroy()
    }

    override fun addCallback(listener: TransitionProgressListener) {
        scopedUnfoldTransitionProgressProvider.addCallback(listener)
    }

    override fun removeCallback(listener: TransitionProgressListener) {
        scopedUnfoldTransitionProgressProvider.removeCallback(listener)
    }
}
