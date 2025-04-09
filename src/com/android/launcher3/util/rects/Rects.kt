

package com.android.launcher3.util.rects

import android.graphics.Rect
import android.view.View

/** Copy the coordinates of the [view] relative to its parent into this rectangle. */
fun Rect.set(view: View) {
    set(0, 0, view.width, view.height)
    offset(view.x.toInt(), view.y.toInt())
}
