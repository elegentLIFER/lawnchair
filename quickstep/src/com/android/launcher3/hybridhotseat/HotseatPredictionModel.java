
package com.android.launcher3.hybridhotseat;

import static com.android.launcher3.LauncherSettings.Favorites.CONTAINER_HOTSEAT_PREDICTION;
import static com.android.launcher3.model.PredictionHelper.getAppTargetFromItemInfo;
import static com.android.launcher3.model.PredictionHelper.isTrackedForHotseatPrediction;
import static com.android.launcher3.model.PredictionHelper.wrapAppTargetWithItemLocation;

import android.app.prediction.AppTarget;
import android.app.prediction.AppTargetEvent;
import android.content.Context;
import android.os.Bundle;

import com.android.launcher3.model.BgDataModel;
import com.android.launcher3.model.BgDataModel.FixedContainerItems;
import com.android.launcher3.model.data.ItemInfo;

import java.util.ArrayList;

/**
 * Model helper for app predictions in workspace
 */
public class HotseatPredictionModel {
    private static final String BUNDLE_KEY_PIN_EVENTS = "pin_events";
    private static final String BUNDLE_KEY_CURRENT_ITEMS = "current_items";

    /**
     * Creates and returns bundle using workspace items
     */
    public static Bundle convertDataModelToAppTargetBundle(Context context, BgDataModel dataModel) {
        Bundle bundle = new Bundle();
        ArrayList<AppTargetEvent> events = new ArrayList<>();
        ArrayList<ItemInfo> workspaceItems = dataModel.getAllWorkspaceItems();
        for (ItemInfo item : workspaceItems) {
            AppTarget target = getAppTargetFromItemInfo(context, item);
            if (target != null && !isTrackedForHotseatPrediction(item)) continue;
            events.add(wrapAppTargetWithItemLocation(target, AppTargetEvent.ACTION_PIN, item));
        }
        ArrayList<AppTarget> currentTargets = new ArrayList<>();
        FixedContainerItems hotseatItems = dataModel.extraItems.get(CONTAINER_HOTSEAT_PREDICTION);
        if (hotseatItems != null) {
            for (ItemInfo itemInfo : hotseatItems.items) {
                AppTarget target = getAppTargetFromItemInfo(context, itemInfo);
                if (target != null) currentTargets.add(target);
            }
        }
        bundle.putParcelableArrayList(BUNDLE_KEY_PIN_EVENTS, events);
        bundle.putParcelableArrayList(BUNDLE_KEY_CURRENT_ITEMS, currentTargets);
        return bundle;
    }
}
