
package com.android.launcher3.widget.picker;

import android.graphics.Bitmap;
import android.util.Pair;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.android.launcher3.R;
import com.android.launcher3.model.WidgetItem;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/** A {@link ViewHolder} for showing widgets of an app in the full widget picker. */
public final class WidgetsRowViewHolder extends ViewHolder {

    public final WidgetsListTableView tableContainer;
    public final Map<WidgetItem, Bitmap> previewCache = new HashMap<>();
    Consumer<Pair<WidgetItem, Bitmap>> mDataCallback;

    public WidgetsRowViewHolder(View v) {
        super(v);

        tableContainer = v.findViewById(R.id.widgets_table);
    }

    /**
     * When the preview is loaded we callback to notify that the preview loaded and we rebind the
     * view.
     *
     * @param data is the payload which is needed when binding the view.
     */
    public void onPreviewLoaded(Pair<WidgetItem, Bitmap> data) {
        if (mDataCallback != null) {
            mDataCallback.accept(data);
        }
        if (getBindingAdapter() != null) {
            getBindingAdapter().notifyItemChanged(getBindingAdapterPosition(), data);
        }
    }
}
