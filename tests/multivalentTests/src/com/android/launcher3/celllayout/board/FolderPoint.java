

package com.android.launcher3.celllayout.board;

import android.graphics.Point;

public class FolderPoint {
    public Point coord;
    public char mType;

    public FolderPoint(Point coord, char type) {
        this.coord = coord;
        mType = type;
    }

    /**
     * [A-Z]: Represents a folder and number of icons in the folder is represented by
     * the order of letter in the alphabet, A=2, B=3, C=4 ... etc.
     */
    public int getNumberIconsInside() {
        return (mType - 'A') + 2;
    }
}
