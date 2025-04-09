
package com.android.launcher3.celllayout

import android.graphics.Rect
import android.util.ArrayMap
import android.view.View
import com.android.launcher3.util.CellAndSpan

/** Represents the solution to a reorder of items in the Workspace. */
class ItemConfiguration : CellAndSpan() {
    @JvmField val map = ArrayMap<View, CellAndSpan>()
    private val savedMap = ArrayMap<View, CellAndSpan>()

    @JvmField val sortedViews = ArrayList<View>()

    @JvmField var intersectingViews: ArrayList<View> = ArrayList()

    @JvmField var isSolution = false
    fun save() {
        // Copy current state into savedMap
        map.forEach { (k, v) -> savedMap[k]?.copyFrom(v) }
    }

    fun restore() {
        // Restore current state from savedMap
        savedMap.forEach { (k, v) -> map[k]?.copyFrom(v) }
    }

    fun add(v: View, cs: CellAndSpan) {
        map[v] = cs
        savedMap[v] = CellAndSpan()
        sortedViews.add(v)
    }

    fun area(): Int {
        return spanX * spanY
    }

    fun getBoundingRectForViews(views: ArrayList<View>, outRect: Rect) {
        views
            .mapNotNull { v -> map[v] }
            .forEachIndexed { i, c ->
                if (i == 0) outRect.set(c.cellX, c.cellY, c.cellX + c.spanX, c.cellY + c.spanY)
                else outRect.union(c.cellX, c.cellY, c.cellX + c.spanX, c.cellY + c.spanY)
            }
    }
}
