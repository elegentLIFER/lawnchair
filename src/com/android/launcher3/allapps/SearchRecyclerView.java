
package com.android.launcher3.allapps;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;

import com.android.launcher3.views.RecyclerViewFastScroller;

/** A RecyclerView for AllApps Search results. */
public class SearchRecyclerView extends AllAppsRecyclerView {

    public SearchRecyclerView(Context context) {
        this(context, null);
    }

    public SearchRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SearchRecyclerView(Context context, AttributeSet attrs, int defStyleAttr,
            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void updatePoolSize() {
        RecycledViewPool pool = getRecycledViewPool();
        pool.setMaxRecycledViews(AllAppsGridAdapter.VIEW_TYPE_ICON, mNumAppsPerRow);
        // TODO(b/206905515): Add maxes for other View types.
    }

    @Override
    public boolean supportsFastScrolling() {
        return false;
    }

    @Override
    public RecyclerViewFastScroller getScrollbar() {
        return null;
    }
}
