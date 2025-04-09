

package com.android.launcher3;

/**
 * This interface should be implemented for any container/view that has a CellLayout as a children.
 */
public interface CellLayoutContainer {

    /**
     * Get the CellLayoutId for the given cellLayout.
     */
    int getCellLayoutId(CellLayout cellLayout);

    /**
     * Get the index of the given CellLayout out of all the other CellLayouts.
     */
    int getCellLayoutIndex(CellLayout cellLayout);

    /**
     * The total number of CellLayouts in the container.
     */
    int getPanelCount();

    /**
     * Used for accessibility, it returns the string that the assistant is going to say when
     * referring to the given CellLayout.
     */
    String getPageDescription(int pageIndex);
}
