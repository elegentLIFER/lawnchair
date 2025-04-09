

package com.android.systemui.plugins;

import android.view.View;
import android.view.ViewGroup;

import com.android.systemui.plugins.annotations.ProvidesInterface;

/**
 * Implement this plugin interface to add a row of views to the top of the all apps drawer.
 */
@ProvidesInterface(action = AllAppsRow.ACTION, version = AllAppsRow.VERSION)
public interface AllAppsRow extends Plugin {
    String ACTION = "com.android.systemui.action.PLUGIN_ALL_APPS_ACTIONS";
    int VERSION = 1;

    /**
     * Setup the row and return the parent view.
     * @param parent The ViewGroup to which launcher will add this row.
     */
    View setup(ViewGroup parent);

    /**
     * @return The height to reserve in all apps for your views.
     */
    int getExpectedHeight();

    /**
     * Update launcher whenever {@link #getExpectedHeight()} changes.
     */
    void setOnHeightUpdatedListener(OnHeightUpdatedListener onHeightUpdatedListener);

    interface OnHeightUpdatedListener {
        void onHeightUpdated();
    }
}
