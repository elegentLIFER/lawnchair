

package com.android.wm.shell.windowdecor.additionalviewcontainer

import android.view.SurfaceControl
import android.view.View
import com.android.wm.shell.windowdecor.WindowDecoration

/**
 * Class for additional view containers associated with a [WindowDecoration].
 */
abstract class AdditionalViewContainer internal constructor(
) {
    abstract val view: View?

    /** Release the view associated with this container and perform needed cleanup. */
    abstract fun releaseView()

    /** Reposition the view container using provided coordinates. */
    abstract fun setPosition(t: SurfaceControl.Transaction, x: Float, y: Float)
}
