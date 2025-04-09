
package com.android.launcher3.allapps.search;

import static com.android.launcher3.allapps.BaseAllAppsAdapter.VIEW_TYPE_EMPTY_SEARCH;
import static com.android.launcher3.util.Executors.MAIN_EXECUTOR;

import android.content.Context;
import android.os.Handler;

import androidx.annotation.AnyThread;

import com.android.launcher3.LauncherAppState;
import com.android.launcher3.allapps.BaseAllAppsAdapter.AdapterItem;
import com.android.launcher3.model.data.AppInfo;
import com.android.launcher3.search.SearchAlgorithm;
import com.android.launcher3.search.SearchCallback;
import com.android.launcher3.search.StringMatcherUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * The default search implementation.
 */
public class DefaultAppSearchAlgorithm implements SearchAlgorithm<AdapterItem> {

    protected static final int MAX_RESULTS_COUNT = 5;

    private final LauncherAppState mAppState;
    private final Handler mResultHandler;
    private final boolean mAddNoResultsMessage;

    public DefaultAppSearchAlgorithm(Context context) {
        this(context, false);
    }

    public DefaultAppSearchAlgorithm(Context context, boolean addNoResultsMessage) {
        mAppState = LauncherAppState.getInstance(context);
        mResultHandler = new Handler(MAIN_EXECUTOR.getLooper());
        mAddNoResultsMessage = addNoResultsMessage;
    }

    @Override
    public void cancel(boolean interruptActiveRequests) {
        if (interruptActiveRequests) {
            mResultHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public void doSearch(String query, SearchCallback<AdapterItem> callback) {
        mAppState.getModel().enqueueModelUpdateTask((taskController, dataModel, apps) ->  {
            ArrayList<AdapterItem> result = getTitleMatchResult(apps.data, query);
            if (mAddNoResultsMessage && result.isEmpty()) {
                result.add(getEmptyMessageAdapterItem(query));
            }
            mResultHandler.post(() -> callback.onSearchResult(query, result));
        });
    }

    private static AdapterItem getEmptyMessageAdapterItem(String query) {
        AdapterItem item = new AdapterItem(VIEW_TYPE_EMPTY_SEARCH);
        // Add a place holder info to propagate the query
        AppInfo placeHolder = new AppInfo();
        placeHolder.title = query;
        item.itemInfo = placeHolder;
        return item;
    }

    /**
     * Filters {@link AppInfo}s matching specified query
     */
    @AnyThread
    private static ArrayList<AdapterItem> getTitleMatchResult(List<AppInfo> apps, String query) {
        // Do an intersection of the words in the query and each title, and filter out
        // all the
        // apps that don't match all of the words in the query.
        final String queryTextLower = query.toLowerCase();
        final ArrayList<AdapterItem> result = new ArrayList<>();
        StringMatcherUtility.StringMatcher matcher = StringMatcherUtility.StringMatcher.getInstance();

        int resultCount = 0;
        int total = apps.size();
        for (int i = 0; i < total && resultCount < MAX_RESULTS_COUNT; i++) {
            AppInfo info = apps.get(i);
            if (StringMatcherUtility.matches(queryTextLower, info.title.toString(), matcher)) {
                result.add(AdapterItem.asApp(info));
                resultCount++;
            }
        }
        return result;
    }
}
