
package com.android.launcher3.celllayout

import android.graphics.Canvas

/** A Delegated cell Drawing for drawing on CellLayout */
abstract class DelegatedCellDrawing {
    @JvmField var mDelegateCellX = 0
    @JvmField var mDelegateCellY = 0

    /** Draw under CellLayout */
    abstract fun drawUnderItem(canvas: Canvas)

    /** Draw over CellLayout */
    abstract fun drawOverItem(canvas: Canvas)
}
