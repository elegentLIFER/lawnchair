

package com.android.wm.shell.windowdecor.additionalviewcontainer

import android.view.SurfaceControl
import android.view.SurfaceControlViewHost
import java.util.function.Supplier

/**
 * An [AdditionalViewContainer] that uses a [SurfaceControlViewHost] to show the window.
 * Intended for view containers in freeform tasks that do not extend beyond task bounds.
 */
class AdditionalViewHostViewContainer(
    private val windowSurface: SurfaceControl,
    private val windowViewHost: SurfaceControlViewHost,
    private val transactionSupplier: Supplier<SurfaceControl.Transaction>,
) : AdditionalViewContainer() {

    override val view
        get() = windowViewHost.view

    override fun releaseView() {
        windowViewHost.release()
        val t = transactionSupplier.get()
        t.remove(windowSurface)
        t.apply()
    }

    override fun setPosition(t: SurfaceControl.Transaction, x: Float, y: Float) {
        t.setPosition(windowSurface, x, y)
    }
}
