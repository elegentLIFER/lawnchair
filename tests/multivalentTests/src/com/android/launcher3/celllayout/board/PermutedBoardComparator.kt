
package com.android.launcher3.celllayout.board

import android.graphics.Point

/**
 * Compares two [CellLayoutBoard] and returns 0 if they contain the same widgets and icons even if
 * they are in different positions i.e. in a different permutation.
 */
class PermutedBoardComparator : Comparator<CellLayoutBoard> {

    /**
     * The key for the set is the span since the widgets could change location but shouldn't change
     * size
     */
    private fun boardToSpanCountMap(widgets: List<WidgetRect>) =
        widgets.groupingBy { Point(it.spanX, it.spanY) }.eachCount()
    override fun compare(
        cellLayoutBoard: CellLayoutBoard,
        otherCellLayoutBoard: CellLayoutBoard
    ): Int {
        return if (
            boardToSpanCountMap(cellLayoutBoard.widgets) !=
                boardToSpanCountMap(otherCellLayoutBoard.widgets)
        ) {
            1
        } else cellLayoutBoard.icons.size.compareTo(otherCellLayoutBoard.icons.size)
    }
}
