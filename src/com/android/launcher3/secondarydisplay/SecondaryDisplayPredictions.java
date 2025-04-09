
package com.android.launcher3.secondarydisplay;

import android.content.Context;

import com.android.launcher3.R;
import com.android.launcher3.model.BgDataModel;
import com.android.launcher3.util.ResourceBasedOverride;

/**
 * Exposes Quickstep app prediction row APIs to {@link SecondaryDisplayLauncher}.
 */
public class SecondaryDisplayPredictions implements ResourceBasedOverride {
    /**
     * Creates a {@link SecondaryDisplayPredictions} instance.
     */
    static SecondaryDisplayPredictions newInstance(Context context) {
        return Overrides.getObject(
                SecondaryDisplayPredictions.class, context,
                R.string.secondary_display_predictions_class);
    }

    /**
     * Setup/update app divider separating app predictions from All Apps.
     */
    void updateAppDivider() {
    }

    /**
     * Set predicted apps in top of app drawer.
     */
    public void setPredictedApps(BgDataModel.FixedContainerItems item) {
    }
}
