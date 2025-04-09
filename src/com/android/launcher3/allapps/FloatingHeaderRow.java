
package com.android.launcher3.allapps;

import android.view.View;

/**
 * A abstract representation of a row in all-apps view
 */
public interface FloatingHeaderRow {

    FloatingHeaderRow[] NO_ROWS = new FloatingHeaderRow[0];

    void setup(FloatingHeaderView parent, FloatingHeaderRow[] allRows, boolean tabsHidden);

    int getExpectedHeight();

    /**
     * Returns true if the row should draw based on its current position and layout.
     */
    boolean shouldDraw();

    /**
     * Returns true if the view has anything worth drawing. This is different than
     * {@link #shouldDraw()} as this is called earlier in the layout to determine the view
     * position.
     */
    boolean hasVisibleContent();

    /**
     * Scrolls the content vertically.
     * @param scroll scrolled distance in pixels for active recyclerview.
     * @param isScrolledOut bool to determine if row is scrolled out of view
     */
    void setVerticalScroll(int scroll, boolean isScrolledOut);

    Class<? extends FloatingHeaderRow> getTypeClass();

    /**
     * Returns a child that has focus to be launched by the IME.
     */
    View getFocusedChild();

    /**
     * Returns true if view is currently visible
     */
    default boolean isVisible() {
        return shouldDraw();
    }
}
