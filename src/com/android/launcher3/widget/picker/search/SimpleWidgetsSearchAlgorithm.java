

package com.android.launcher3.widget.picker.search;

import static com.android.launcher3.search.StringMatcherUtility.matches;

import android.os.Handler;

import com.android.launcher3.model.WidgetItem;
import com.android.launcher3.popup.PopupDataProvider;
import com.android.launcher3.search.SearchAlgorithm;
import com.android.launcher3.search.SearchCallback;
import com.android.launcher3.search.StringMatcherUtility.StringMatcher;
import com.android.launcher3.widget.model.WidgetsListBaseEntry;
import com.android.launcher3.widget.model.WidgetsListContentEntry;
import com.android.launcher3.widget.model.WidgetsListHeaderEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link SearchAlgorithm} that posts a task to query on the main thread.
 */
public final class SimpleWidgetsSearchAlgorithm implements SearchAlgorithm<WidgetsListBaseEntry> {

    private final Handler mResultHandler;
    private final PopupDataProvider mDataProvider;

    public SimpleWidgetsSearchAlgorithm(PopupDataProvider dataProvider) {
        mResultHandler = new Handler();
        mDataProvider = dataProvider;
    }

    @Override
    public void doSearch(String query, SearchCallback<WidgetsListBaseEntry> callback) {
        ArrayList<WidgetsListBaseEntry> result = getFilteredWidgets(mDataProvider, query);
        mResultHandler.post(() -> callback.onSearchResult(query, result));
    }

    @Override
    public void cancel(boolean interruptActiveRequests) {
        if (interruptActiveRequests) {
            mResultHandler.removeCallbacksAndMessages(/*token= */null);
        }
    }

    /**
     * Returns entries for all matched widgets
     */
    public static ArrayList<WidgetsListBaseEntry> getFilteredWidgets(
            PopupDataProvider dataProvider, String input) {
        ArrayList<WidgetsListBaseEntry> results = new ArrayList<>();
        dataProvider.getAllWidgets().stream()
                .filter(entry -> entry instanceof WidgetsListHeaderEntry)
                .forEach(headerEntry -> {
                    List<WidgetItem> matchedWidgetItems = filterWidgetItems(
                            input, headerEntry.mPkgItem.title.toString(), headerEntry.mWidgets);
                    if (matchedWidgetItems.size() > 0) {
                        results.add(WidgetsListHeaderEntry.createForSearch(headerEntry.mPkgItem,
                                headerEntry.mTitleSectionName, matchedWidgetItems));
                        results.add(new WidgetsListContentEntry(headerEntry.mPkgItem,
                                headerEntry.mTitleSectionName, matchedWidgetItems));
                    }
                });
        return results;
    }

    private static List<WidgetItem> filterWidgetItems(String query, String packageTitle,
            List<WidgetItem> items) {
        StringMatcher matcher = StringMatcher.getInstance();
        if (matches(query, packageTitle, matcher)) {
            return items;
        }
        return items.stream()
                .filter(item -> matches(query, item.label, matcher))
                .collect(Collectors.toList());
    }
}
