
package com.android.launcher3.util

import android.content.res.Resources
import androidx.core.content.res.getDimensionOrThrow
import androidx.core.content.res.use
import com.android.launcher3.R
import kotlin.math.max

class IconSizeSteps(res: Resources) {
    private val steps: List<Int>
    val minimumIconLabelSize: Int

    init {
        steps =
            res.obtainTypedArray(R.array.icon_size_steps).use {
                (0 until it.length()).map { step -> it.getDimensionOrThrow(step).toInt() }.sorted()
            }
        minimumIconLabelSize = res.getDimensionPixelSize(R.dimen.minimum_icon_label_size)
    }

    fun minimumIconSize(): Int = steps[0]

    fun getNextLowerIconSize(iconSizePx: Int): Int {
        return steps[max(0, getIndexForIconSize(iconSizePx) - 1)]
    }

    fun getIconSmallerThan(cellSize: Int): Int {
        return steps.lastOrNull { it <= cellSize } ?: steps[0]
    }

    private fun getIndexForIconSize(iconSizePx: Int): Int {
        return max(0, steps.indexOfFirst { iconSizePx <= it })
    }

    companion object {
        internal const val TEXT_STEP = 1

        // This icon extra step is used for stepping down logic in extreme cases when it's
        // necessary to reduce the icon size below minimum size available in [icon_size_steps].
        internal const val ICON_SIZE_STEP_EXTRA = 2
    }
}
