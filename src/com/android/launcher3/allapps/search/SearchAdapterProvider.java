

package com.android.launcher3.allapps.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.launcher3.allapps.AllAppsGridAdapter;
import com.android.launcher3.views.ActivityContext;

/**
 * A UI expansion wrapper providing for search results
 *
 * @param <T> Context for this adapter provider.
 */
public abstract class SearchAdapterProvider<T extends ActivityContext> {

    protected final T mLauncher;

    public SearchAdapterProvider(T launcher) {
        mLauncher = launcher;
    }

    /**
     * Returns the item decorator.
     */
    public abstract RecyclerView.ItemDecoration getDecorator();

    /**
     * Handles selection event on search adapter item. Returns false if provider can not handle
     * event
     */
    public abstract boolean launchHighlightedItem();

    /**
     * Returns the current highlighted view
     */
    public abstract View getHighlightedItem();

    /**
     * Clear the highlighted view.
     */
    public abstract void clearHighlightedItem();

    /**
     * Returns whether or not viewType can be handled by searchProvider
     */
    public abstract boolean isViewSupported(int viewType);

    /**
     * Called from RecyclerView.Adapter#onBindViewHolder
     */
    public abstract void onBindView(AllAppsGridAdapter.ViewHolder holder, int position);

    /**
     * Called from RecyclerView.Adapter#onCreateViewHolder
     */
    public abstract AllAppsGridAdapter.ViewHolder onCreateViewHolder(LayoutInflater layoutInflater,
            ViewGroup parent, int viewType);

    /**
     * Returns supported item per row combinations supported
     */
    public int[] getSupportedItemsPerRowArray() {
        return new int[]{};
    }

    /**
     * Returns how many cells a view should span
     */
    public int getItemsPerRow(int viewType, int appsPerRow) {
        return appsPerRow;
    }
}
