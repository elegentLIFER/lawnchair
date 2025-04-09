
package com.android.launcher3.util

import com.android.launcher3.Utilities
import kotlin.math.max

class CellContentDimensions(
    var iconSizePx: Int,
    var iconDrawablePaddingPx: Int,
    var iconTextSizePx: Int
) {
    /**
     * This method goes through some steps to reduce the padding between icon and label, icon size
     * and then label size, until it can fit in the [cellHeightPx].
     *
     * @return the height of the content after being sized down.
     */
    fun resizeToFitCellHeight(cellHeightPx: Int, iconSizeSteps: IconSizeSteps): Int {
        var cellContentHeight = getCellContentHeight()

        // Step 1. Decrease drawable padding
        if (cellContentHeight > cellHeightPx) {
            val diff = cellContentHeight - cellHeightPx
            iconDrawablePaddingPx = max(0, iconDrawablePaddingPx - diff)
            cellContentHeight = getCellContentHeight()
        }

        while (
            (iconTextSizePx > iconSizeSteps.minimumIconLabelSize ||
                iconSizePx > iconSizeSteps.minimumIconSize()) && cellContentHeight > cellHeightPx
        ) {
            // Step 2. Decrease icon size
            iconSizePx = iconSizeSteps.getNextLowerIconSize(iconSizePx)
            cellContentHeight = getCellContentHeight()

            // Step 3. Decrease label size
            if (
                cellContentHeight > cellHeightPx &&
                    iconTextSizePx > iconSizeSteps.minimumIconLabelSize
            ) {
                iconTextSizePx =
                    max(
                        iconSizeSteps.minimumIconLabelSize,
                        iconTextSizePx - IconSizeSteps.TEXT_STEP
                    )
                cellContentHeight = getCellContentHeight()
            }
        }

        // For some cases, depending on the display size, the content might not fit inside the
        // cell height after considering the minimum icon and label size allowed.
        // For these extreme cases, we will allow the icon size to be smaller than
        // [IconSizeSteps.minimumIconSize] to fit inside the cell height without cropping.
        while (
            cellContentHeight > cellHeightPx && iconSizePx > IconSizeSteps.ICON_SIZE_STEP_EXTRA
        ) {
            iconSizePx -= IconSizeSteps.ICON_SIZE_STEP_EXTRA
            cellContentHeight = getCellContentHeight()
        }

        return cellContentHeight
    }

    /** Calculate new cellContentHeight */
    fun getCellContentHeight(): Int {
        val iconTextHeight = Utilities.calculateTextHeight(iconTextSizePx.toFloat())
        return iconSizePx + iconDrawablePaddingPx + iconTextHeight
    }
}
