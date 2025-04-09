
package com.android.launcher3.secondarydisplay;

import static com.android.launcher3.util.OnboardingPrefs.ALL_APPS_VISITED_COUNT;

import android.content.Context;

import com.android.launcher3.appprediction.AppsDividerView;
import com.android.launcher3.appprediction.PredictionRowView;
import com.android.launcher3.model.BgDataModel;
import com.android.launcher3.views.ActivityContext;

/**
 * Implementation of SecondaryDisplayPredictions.
 */
@SuppressWarnings("unused")
public final class SecondaryDisplayPredictionsImpl extends SecondaryDisplayPredictions {

    private final ActivityContext mActivityContext;
    private final Context mContext;

    public SecondaryDisplayPredictionsImpl(Context context) {
        mContext = context;
        mActivityContext = ActivityContext.lookupContext(context);
    }

    @Override
    void updateAppDivider() {
        mActivityContext.getAppsView().getFloatingHeaderView()
                .findFixedRowByType(AppsDividerView.class)
                .setShowAllAppsLabel(!ALL_APPS_VISITED_COUNT.hasReachedMax(mContext));
        ALL_APPS_VISITED_COUNT.increment(mContext);
    }

    @Override
    public void setPredictedApps(BgDataModel.FixedContainerItems item) {
        mActivityContext.getAppsView().getFloatingHeaderView()
                .findFixedRowByType(PredictionRowView.class)
                .setPredictedApps(item.items);
    }
}
