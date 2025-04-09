
package com.android.launcher3.widget.picker;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.android.launcher3.recyclerview.ViewHolderBinder;
import com.android.launcher3.views.StickyHeaderLayout.EmptySpaceView;
import com.android.launcher3.widget.model.WidgetListSpaceEntry;

import java.util.List;
import java.util.function.IntSupplier;

/**
 * {@link ViewHolderBinder} for binding the top empty space
 */
public class WidgetsSpaceViewHolderBinder
        implements ViewHolderBinder<WidgetListSpaceEntry, ViewHolder> {

    private final IntSupplier mEmptySpaceHeightProvider;

    public WidgetsSpaceViewHolderBinder(IntSupplier emptySpaceHeightProvider) {
        mEmptySpaceHeightProvider = emptySpaceHeightProvider;
    }

    @Override
    public ViewHolder newViewHolder(ViewGroup parent) {
        return new ViewHolder(new EmptySpaceView(parent.getContext())) { };
    }

    @Override
    public void bindViewHolder(ViewHolder holder, WidgetListSpaceEntry data,
            @ListPosition int position, List<Object> payloads) {
        ((EmptySpaceView) holder.itemView).setFixedHeight(mEmptySpaceHeightProvider.getAsInt());
    }
}
