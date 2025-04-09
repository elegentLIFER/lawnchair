
package com.android.launcher3.celllayout.testgenerator

import android.graphics.Rect
import com.android.launcher3.celllayout.board.CellLayoutBoard
import java.util.Random

class RandomMultiBoardGenerator(generator: Random) : RandomBoardGenerator(generator) {
    override fun generateBoard(
        width: Int,
        height: Int,
        remainingEmptySpaces: Int
    ): CellLayoutBoard {
        val cellLayoutBoard = CellLayoutBoard(width, height)
        fillBoard(cellLayoutBoard, Rect(0, 0, width / 2, height), remainingEmptySpaces / 2)
        return fillBoard(
            cellLayoutBoard,
            Rect(width / 2, 0, width, height),
            remainingEmptySpaces / 2
        )
    }
}
