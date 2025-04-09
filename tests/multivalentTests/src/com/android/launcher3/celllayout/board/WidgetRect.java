

package com.android.launcher3.celllayout.board;

import android.graphics.Rect;

public class WidgetRect {
    public char mType;
    public Rect mBounds;

    public WidgetRect(char type, Rect bounds) {
        this.mType = type;
        this.mBounds = bounds;
    }

    public int getSpanX() {
        return mBounds.right - mBounds.left + 1;
    }

    public int getSpanY() {
        return mBounds.top - mBounds.bottom + 1;
    }

    public int getCellX() {
        return mBounds.left;
    }

    public int getCellY() {
        return mBounds.bottom;
    }

    boolean shouldIgnore() {
        return this.mType == CellType.IGNORE;
    }

    boolean contains(int x, int y) {
        return mBounds.contains(x, y);
    }

    @Override
    public String toString() {
        return "WidgetRect type = " + mType + " x = " + getCellX() + " | y " + getCellY()
                + " xs = " + getSpanX() + " ys = " + getSpanY();
    }
}
