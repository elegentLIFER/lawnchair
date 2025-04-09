
package com.android.launcher3.allapps.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.launcher3.BubbleTextView;
import com.android.launcher3.allapps.AllAppsGridAdapter;
import com.android.launcher3.model.data.ItemInfo;
import com.android.launcher3.views.ActivityContext;

/**
 * Provides views for local search results.
 */
public class DefaultSearchAdapterProvider extends SearchAdapterProvider<ActivityContext> {
    private View mHighlightedView;

    public DefaultSearchAdapterProvider(ActivityContext launcher) {
        super(launcher);
    }

    @Override
    public RecyclerView.ItemDecoration getDecorator() {
        return null;
    }

    @Override
    public void onBindView(AllAppsGridAdapter.ViewHolder holder, int position) {
        if (position == 0) {
            mHighlightedView = holder.itemView;
        }
    }

    @Override
    public boolean isViewSupported(int viewType) {
        return false;
    }

    @Override
    public AllAppsGridAdapter.ViewHolder onCreateViewHolder(LayoutInflater layoutInflater,
            ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public boolean launchHighlightedItem() {
        if (mHighlightedView instanceof BubbleTextView
                && mHighlightedView.getTag() instanceof ItemInfo) {
            ItemInfo itemInfo = (ItemInfo) mHighlightedView.getTag();
            return mLauncher.startActivitySafely(
                    mHighlightedView, itemInfo.getIntent(), itemInfo) != null;
        }
        return false;
    }

    @Override
    public View getHighlightedItem() {
        return mHighlightedView;
    }

    @Override
    public void clearHighlightedItem() {
        mHighlightedView = null;
    }
}
