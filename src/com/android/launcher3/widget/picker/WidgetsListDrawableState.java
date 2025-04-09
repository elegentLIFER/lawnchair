
package com.android.launcher3.widget.picker;

/**
 * Different possible list position states for an item in the widgets list to have. Note that only
 * headers use the expanded state.
 */
enum WidgetsListDrawableState {
    FIRST(new int[]{android.R.attr.state_first}),
    MIDDLE(new int[]{android.R.attr.state_middle}),
    LAST(new int[]{android.R.attr.state_last}),
    SINGLE(new int[]{android.R.attr.state_single});

    final int[] mStateSet;

    WidgetsListDrawableState(int[] stateSet) {
        mStateSet = stateSet;
    }

    static WidgetsListDrawableState obtain(boolean isFirst, boolean isLast) {
        if (isFirst && isLast) return SINGLE;
        if (isFirst) return FIRST;
        if (isLast) return LAST;
        return MIDDLE;
    }
}
