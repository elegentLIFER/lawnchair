
package com.android.launcher3.widget.picker;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;

/**
 * A {@link ViewHolder} for {@link WidgetsListHeader} of an app, which renders the app icon, the app
 * name, label and a button for showing / hiding widgets.
 */
public final class WidgetsListHeaderHolder extends ViewHolder {
    final WidgetsListHeader mWidgetsListHeader;

    public WidgetsListHeaderHolder(WidgetsListHeader view) {
        super(view);

        mWidgetsListHeader = view;
    }
}
