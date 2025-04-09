

package com.android.launcher3.widget.picker.search;

import com.android.launcher3.popup.PopupDataProvider;

/**
 * Interface for a widgets picker search bar.
 */
public interface WidgetsSearchBar {
    /**
     * Attaches a controller to the search bar which interacts with {@code searchModeListener}.
     */
    void initialize(PopupDataProvider dataProvider, SearchModeListener searchModeListener);

    /**
     * Clears search bar.
     */
    void reset();

    /** Returns {@code true} if the search bar is in focus. */
    boolean isSearchBarFocused();

    /**
     * Clears focus from search bar.
     */
    void clearSearchBarFocus();

    /**
     * Sets the vertical location, in pixels, of this search bar relative to its top position.
     */
    void setTranslationY(float translationY);
}
