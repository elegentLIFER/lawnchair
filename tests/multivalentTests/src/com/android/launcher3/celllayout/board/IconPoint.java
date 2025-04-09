

package com.android.launcher3.celllayout.board;

import android.graphics.Point;

public class IconPoint {
    public Point coord;
    public char mType;

    public IconPoint(Point coord, char type) {
        this.coord = coord;
        mType = type;
    }

    public char getType() {
        return mType;
    }

    public void setType(char type) {
        mType = type;
    }

    public Point getCoord() {
        return coord;
    }

    public void setCoord(Point coord) {
        this.coord = coord;
    }
}
