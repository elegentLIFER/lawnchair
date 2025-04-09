

package com.android.launcher3.widget.picker;

import androidx.recyclerview.widget.DiffUtil.Callback;

import com.android.launcher3.widget.model.WidgetsListBaseEntry;

import java.util.List;

/**
 * DiffUtil callback to compare widgets
 */
public class WidgetsDiffCallback extends Callback {

    private final List<WidgetsListBaseEntry> mOldEntries;
    private final List<WidgetsListBaseEntry> mNewEntries;

    public WidgetsDiffCallback(
            List<WidgetsListBaseEntry> oldEntries,
            List<WidgetsListBaseEntry> newEntries) {
        mOldEntries = oldEntries;
        mNewEntries = newEntries;
    }

    @Override
    public int getOldListSize() {
        return mOldEntries.size();
    }

    @Override
    public int getNewListSize() {
        return mNewEntries.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        // Items are same if they point to the same package entry
        WidgetsListBaseEntry oldItem = mOldEntries.get(oldItemPosition);
        WidgetsListBaseEntry newItem = mNewEntries.get(newItemPosition);
        return oldItem.getClass().equals(newItem.getClass())
                && oldItem.mPkgItem.equals(newItem.mPkgItem);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        // Always update all entries since the icon may have changed
        return false;
    }
}
