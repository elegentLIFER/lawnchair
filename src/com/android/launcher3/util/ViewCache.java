
package com.android.launcher3.util;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.launcher3.R;

/**
 * Utility class to cache views at an activity level
 */
public class ViewCache {

    protected final SparseArray<CacheEntry> mCache = new SparseArray();

    public void setCacheSize(int layoutId, int size) {
        mCache.put(layoutId, new CacheEntry(size));
    }

    public <T extends View> T getView(int layoutId, Context context, ViewGroup parent) {
        CacheEntry entry = mCache.get(layoutId);
        if (entry == null) {
            entry = new CacheEntry(1);
            mCache.put(layoutId, entry);
        }

        T result;
        if (entry.mCurrentSize > 0) {
            entry.mCurrentSize --;
            result = (T) entry.mViews[entry.mCurrentSize];
            entry.mViews[entry.mCurrentSize] = null;
        } else {
            result = (T) LayoutInflater.from(context).inflate(layoutId, parent, false);
            result.setTag(R.id.cache_entry_tag_id, entry);
        }
        return result;
    }

    public void recycleView(int layoutId, View view) {
        CacheEntry entry = mCache.get(layoutId);
        if (entry != view.getTag(R.id.cache_entry_tag_id)) {
            // Since this view was created, the cache has been reset. The view should not be
            // recycled since this means the environment could also have changed, requiring new
            // view setup.
            return;
        }
        if (entry != null && entry.mCurrentSize < entry.mMaxSize) {
            entry.mViews[entry.mCurrentSize] = view;
            entry.mCurrentSize++;
        }
    }

    private static class CacheEntry {

        final int mMaxSize;
        final View[] mViews;

        int mCurrentSize;

        public CacheEntry(int maxSize) {
            mMaxSize = maxSize;
            mViews = new View[maxSize];
            mCurrentSize = 0;
        }
    }
}
