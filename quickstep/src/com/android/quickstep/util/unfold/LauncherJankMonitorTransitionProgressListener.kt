
package com.android.quickstep.util.unfold

import android.view.View
import com.android.internal.jank.Cuj
import com.android.systemui.shared.system.InteractionJankMonitorWrapper
import com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
import java.util.function.Supplier

/** Reports beginning and end of the unfold animation to interaction jank monitor */
class LauncherJankMonitorTransitionProgressListener(
    private val attachedViewProvider: Supplier<View>
) : TransitionProgressListener {

    override fun onTransitionStarted() {
        InteractionJankMonitorWrapper.begin(
            attachedViewProvider.get(),
            Cuj.CUJ_LAUNCHER_UNFOLD_ANIM
        )
    }

    override fun onTransitionFinished() {
        InteractionJankMonitorWrapper.end(Cuj.CUJ_LAUNCHER_UNFOLD_ANIM)
    }
}
