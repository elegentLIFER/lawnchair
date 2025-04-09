
package com.android.launcher3.celllayout;

import android.graphics.Point;

import com.android.launcher3.celllayout.board.CellLayoutBoard;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReorderTestCase {
    public List<CellLayoutBoard> mStart;
    public Point moveMainTo;
    public List<List<CellLayoutBoard>> mEnd;

    public ReorderTestCase(List<CellLayoutBoard> start, Point moveMainTo,
            List<CellLayoutBoard>... end) {
        mStart = start;
        this.moveMainTo = moveMainTo;
        mEnd = Arrays.asList(end);
    }

    public ReorderTestCase(String start, Point moveMainTo, String ... end) {
        mStart = CellLayoutBoard.boardListFromString(start);
        this.moveMainTo = moveMainTo;
        mEnd = Arrays
                .asList(end)
                .stream()
                .map(CellLayoutBoard::boardListFromString)
                .collect(Collectors.toList());
    }
}
