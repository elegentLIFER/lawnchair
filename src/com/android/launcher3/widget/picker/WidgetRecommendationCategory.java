

package com.android.launcher3.widget.picker;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import java.util.Objects;

/**
 * A category of widget recommendations displayed in the widget picker (launched from "Widgets"
 * option in the pop-up opened on long press of launcher workspace).
 */
public class WidgetRecommendationCategory implements Comparable<WidgetRecommendationCategory> {
    /** Resource id that holds the user friendly label for the category. */
    @StringRes
    public final int categoryTitleRes;
    /**
     * Relative order of this category with respect to other categories.
     *
     * <p>Category with lowest order is displayed first in the recommendations section.</p>
     */
    public final int order;

    public WidgetRecommendationCategory(@StringRes int categoryTitleRes, int order) {
        this.categoryTitleRes = categoryTitleRes;
        this.order = order;
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryTitleRes, order);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof WidgetRecommendationCategory category)) {
            return false;
        }
        return categoryTitleRes == category.categoryTitleRes
                && order == category.order;
    }

    @Override
    public int compareTo(WidgetRecommendationCategory widgetRecommendationCategory) {
        return order - widgetRecommendationCategory.order;
    }
}
