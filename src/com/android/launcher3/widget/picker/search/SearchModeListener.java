
package com.android.launcher3.widget.picker.search;

import com.android.launcher3.widget.model.WidgetsListBaseEntry;

import java.util.List;

/**
 * A listener to help with widgets picker search.
 */
public interface SearchModeListener {
    /**
     * Notifies the subscriber when user enters widget picker search mode.
     */
    void enterSearchMode(boolean shouldLog);

    /**
     * Notifies the subscriber when user exits widget picker search mode.
     */
    void exitSearchMode();

    /**
     * Notifies the subscriber with search results.
     */
    void onSearchResults(List<WidgetsListBaseEntry> entries);
}
