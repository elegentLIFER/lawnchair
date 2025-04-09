
package com.android.launcher3.search;

/**
 * An interface for handling search.
 *
 * @param <T> Search Result type
 */
public interface SearchAlgorithm<T> {

    /**
     * Performs search and sends the result to {@link SearchCallback}.
     */
    void doSearch(String query, SearchCallback<T> callback);

    /**
     * Performs search with {@code query} and the {@code suggestedQueries}/
     */
    default void doSearch(String query, String[] suggestedQueries, SearchCallback<T> callback) {
        doSearch(query, callback);
    }

    /**
     * Cancels any active request.
     */
    void cancel(boolean interruptActiveRequests);

    /**
     * Cleans up after search is no longer needed.
     */
    default void destroy() {};
}
