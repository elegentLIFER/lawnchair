

package com.android.launcher3.widget.model;

import com.android.launcher3.model.WidgetItem;
import com.android.launcher3.model.data.ItemInfo;
import com.android.launcher3.model.data.PackageItemInfo;
import com.android.launcher3.widget.WidgetItemComparator;

import java.util.List;
import java.util.stream.Collectors;

/** Holder class to store the package information of an entry shown in the widgets list. */
public abstract class WidgetsListBaseEntry {
    public final PackageItemInfo mPkgItem;

    /**
     * Character that is used as a section name for the {@link ItemInfo#title}.
     * (e.g., "G" will be stored if title is "Google")
     */
    public final String mTitleSectionName;

    public final List<WidgetItem> mWidgets;

    public WidgetsListBaseEntry(PackageItemInfo pkgItem, String titleSectionName,
            List<WidgetItem> items) {
        mPkgItem = pkgItem;
        mTitleSectionName = titleSectionName;
        this.mWidgets =
                items.stream().sorted(new WidgetItemComparator()).collect(Collectors.toList());
    }
}
