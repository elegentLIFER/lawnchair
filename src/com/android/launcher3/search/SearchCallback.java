
package com.android.launcher3.search;

import java.util.ArrayList;

/**
 * An interface for receiving search results.
 *
 * @param <T> Search Result type
 */
public interface SearchCallback<T> {

    // Search Result Codes
    int UNKNOWN = 0;
    int INTERMEDIATE = 1;
    int FINAL = 2;

    /**
     * Called when the search from primary source is complete.
     *
     * @param items list of search results
     */
    void onSearchResult(String query, ArrayList<T> items);

    /**
     * Called when the search from primary source is complete.
     *
     * @param items            list of search results
     * @param searchResultCode indicates if the result is final or intermediate for a given query
     *                         since we can get search results from multiple sources.
     */
    default void onSearchResult(String query, ArrayList<T> items, int searchResultCode) {
        onSearchResult(query, items);
    }

    /**
     * Called when the search results should be cleared.
     */
    void clearSearchResult();
}

