

package com.android.wm.shell.windowdecor.additionalviewcontainer

import android.content.Context
import android.graphics.PixelFormat
import android.view.LayoutInflater
import android.view.SurfaceControl
import android.view.View
import android.view.WindowManager

/**
 * An [AdditionalViewContainer] that uses the system [WindowManager] instance. Intended
 * for view containers that should be above the status bar layer.
 */
class AdditionalSystemViewContainer(
    private val context: Context,
    layoutId: Int,
    taskId: Int,
    x: Int,
    y: Int,
    width: Int,
    height: Int
) : AdditionalViewContainer() {
    override val view: View

    init {
        view = LayoutInflater.from(context).inflate(layoutId, null)
        val lp = WindowManager.LayoutParams(
            width, height, x, y,
            WindowManager.LayoutParams.TYPE_STATUS_BAR_ADDITIONAL,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSPARENT
        )
        lp.title = "Additional view container of Task=$taskId"
        lp.setTrustedOverlay()
        val wm: WindowManager? = context.getSystemService(WindowManager::class.java)
        wm?.addView(view, lp)
    }

    override fun releaseView() {
        context.getSystemService(WindowManager::class.java)?.removeViewImmediate(view)
    }

    override fun setPosition(t: SurfaceControl.Transaction, x: Float, y: Float) {
        val lp = (view.layoutParams as WindowManager.LayoutParams).apply {
            this.x = x.toInt()
            this.y = y.toInt()
        }
        view.layoutParams = lp
    }
}
