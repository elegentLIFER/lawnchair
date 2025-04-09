

package com.android.launcher3.widget.picker;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;

import com.android.launcher3.R;
import com.android.launcher3.model.WidgetItem;
import com.android.launcher3.util.PackageManagerHelper;
import com.android.launcher3.util.Preconditions;
import com.android.launcher3.util.ResourceBasedOverride;

/**
 * A {@link ResourceBasedOverride} that categorizes widget recommendations.
 *
 * <p>Override the {@code widget_recommendation_category_provider_class} resource to provide your
 * own implementation. Method {@code getWidgetRecommendationCategory} is called per widget to get
 * the category.</p>
 */
public class WidgetRecommendationCategoryProvider implements ResourceBasedOverride {
    private static final String TAG = "WidgetRecommendationCategoryProvider";

    /**
     * Retrieve instance of this object that can be overridden in runtime based on the build
     * variant of the application.
     */
    public static WidgetRecommendationCategoryProvider newInstance(Context context) {
        Preconditions.assertWorkerThread();
        return Overrides.getObject(
                WidgetRecommendationCategoryProvider.class, context.getApplicationContext(),
                R.string.widget_recommendation_category_provider_class);
    }

    /**
     * Returns a {@link WidgetRecommendationCategory} for the provided widget item that can be used
     * to display the recommendation grouped by categories.
     */
    @WorkerThread
    @Nullable
    public WidgetRecommendationCategory getWidgetRecommendationCategory(Context context,
            WidgetItem item) {
        // This is a default implementation that uses application category to derive the category to
        // be displayed. The implementation can be overridden in individual launcher customization
        // via the overridden WidgetRecommendationCategoryProvider resource.

        Preconditions.assertWorkerThread();
        try (PackageManagerHelper pmHelper = new PackageManagerHelper(context)) {
            if (item.widgetInfo != null && item.widgetInfo.getComponent() != null) {
                ApplicationInfo applicationInfo = pmHelper.getApplicationInfo(
                        item.widgetInfo.getComponent().getPackageName(), item.widgetInfo.getUser(),
                        0 /* flags */);
                if (applicationInfo != null) {
                    return getCategoryFromApplicationCategory(applicationInfo.category);
                }
            }
        }
        return null;
    }

    /** Maps application category to an appropriate displayable category. */
    private static WidgetRecommendationCategory getCategoryFromApplicationCategory(
            int applicationCategory) {
        if (applicationCategory == ApplicationInfo.CATEGORY_PRODUCTIVITY) {
            return new WidgetRecommendationCategory(
                    R.string.productivity_widget_recommendation_category_label, /*order=*/0);
        }

        if (applicationCategory == ApplicationInfo.CATEGORY_NEWS) {
            return new WidgetRecommendationCategory(
                    R.string.news_widget_recommendation_category_label, /*order=*/1);
        }

        if (applicationCategory == ApplicationInfo.CATEGORY_SOCIAL) {
            return new WidgetRecommendationCategory(
                    R.string.social_widget_recommendation_category_label,
                    /*order=*/3);
        }

        if (applicationCategory == ApplicationInfo.CATEGORY_AUDIO
                || applicationCategory == ApplicationInfo.CATEGORY_VIDEO
                || applicationCategory == ApplicationInfo.CATEGORY_IMAGE) {
            return new WidgetRecommendationCategory(
                    R.string.entertainment_widget_recommendation_category_label,
                    /*order=*/4);
        }

        return new WidgetRecommendationCategory(
                R.string.others_widget_recommendation_category_label, /*order=*/2);
    }
}
