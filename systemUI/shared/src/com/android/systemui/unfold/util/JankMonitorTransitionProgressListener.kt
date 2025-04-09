

package com.android.systemui.unfold.util

import android.view.View
import com.android.internal.jank.InteractionJankMonitor
import com.android.internal.jank.InteractionJankMonitor.CUJ_UNFOLD_ANIM
import com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
import java.util.function.Supplier

class JankMonitorTransitionProgressListener(private val attachedViewProvider: Supplier<View>) :
    TransitionProgressListener {

    private val interactionJankMonitor = InteractionJankMonitor.getInstance()

    override fun onTransitionStarted() {
        interactionJankMonitor.begin(attachedViewProvider.get(), CUJ_UNFOLD_ANIM)
    }

    override fun onTransitionFinished() {
        interactionJankMonitor.end(CUJ_UNFOLD_ANIM)
    }
}
