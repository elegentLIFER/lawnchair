

package com.android.launcher3.widget.picker;

import static com.android.launcher3.widget.picker.WidgetsListAdapter.VIEW_TYPE_WIDGETS_LIST;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

public class WidgetsListItemAnimator extends DefaultItemAnimator {
    public static final int CHANGE_DURATION_MS = 90;
    public static final int MOVE_DURATION_MS = 90;
    public static final int ADD_DURATION_MS = 120;

    public WidgetsListItemAnimator() {
        super();

        // Disable change animations because it disrupts the item focus upon adapter item
        // change.
        setSupportsChangeAnimations(false);
        // Make the moves a bit faster, so that the amount of time for which user sees the
        // bottom-sheet background before "add" animation starts is less making it smoother.
        setChangeDuration(CHANGE_DURATION_MS);
        setMoveDuration(MOVE_DURATION_MS);
        setAddDuration(ADD_DURATION_MS);
    }
    @Override
    public boolean animateChange(RecyclerView.ViewHolder oldHolder,
            RecyclerView.ViewHolder newHolder, int fromLeft, int fromTop, int toLeft,
            int toTop) {
        // As we expand an item, the content / widgets list that appears (with add
        // event) also gets change events as its previews load asynchronously. The
        // super implementation of animateChange cancels the animations on it - breaking
        // the "add animation". Instead, here, we skip "change" animation for content
        // list - because we want it to either appear or disappear. And, the previews
        // themselves have their own animation when loaded, so, we don't need change
        // animations for them anyway. Below, we do-nothing.
        if (oldHolder.getItemViewType() == VIEW_TYPE_WIDGETS_LIST) {
            dispatchChangeStarting(oldHolder, true);
            dispatchChangeFinished(oldHolder, true);
            return true;
        }
        return super.animateChange(oldHolder, newHolder, fromLeft, fromTop, toLeft,
                toTop);
    }
}
