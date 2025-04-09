

package com.android.launcher3.tapl;

import androidx.annotation.NonNull;
import androidx.test.uiautomator.UiObject2;

/** View containing prediction app icons */
public class PredictionRow {

    private static final String PREDICTION_ROW_ID = "prediction_row";
    private static final String PREDICTION_APP_ID = "icon";
    private final LauncherInstrumentation mLauncher;
    private final UiObject2 mAllAppsHeader;
    private final UiObject2 mPredictionRow;

    PredictionRow(LauncherInstrumentation launcherInstrumentation,
            UiObject2 allAppsHeader) {
        mLauncher = launcherInstrumentation;
        mAllAppsHeader = allAppsHeader;
        mPredictionRow = mLauncher.waitForObjectInContainer(mAllAppsHeader,
                PREDICTION_ROW_ID);
        verifyAppsPresentInsidePredictionRow();
        verifyPredictionRowAppsCount();
    }

    /** Verify that one app is present in prediction row view. */
    private void verifyAppsPresentInsidePredictionRow() {
        mLauncher.waitForObjectInContainer(mPredictionRow,
                PREDICTION_APP_ID);
    }

    /** Verify that prediction row apps count is same as launcher apps column count. */
    private void verifyPredictionRowAppsCount() {
        mLauncher.assertEquals("PredictionRow app count mismatch", mLauncher.getNumAllAppsColumns(),
                getPredictionRowAppsCount());
    }

    /**
     * Returns an app icon found in the prediction row. This fails if any icon is not
     * found.
     */
    @NonNull
    private HomeAppIcon getAnyAppIcon() {
        return new AllAppsAppIcon(mLauncher,
                mPredictionRow.findObject(AppIcon.getAnyAppIconSelector()));
    }

    /**
     * Returns the size of prediction row apps count.
     */
    private int getPredictionRowAppsCount() {
        try (LauncherInstrumentation.Closable c = mLauncher.addContextLayer(
                "want to get all prediction row icons")) {
            return mPredictionRow.findObjects(AppIcon.getAnyAppIconSelector()).size();
        }
    }
}
