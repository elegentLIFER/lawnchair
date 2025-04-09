

package com.android.launcher3.widget.model;

import android.os.Process;

import com.android.launcher3.model.data.PackageItemInfo;

import java.util.Collections;

/**
 * Entry representing the top empty space
 */
public class WidgetListSpaceEntry extends WidgetsListBaseEntry {

    public WidgetListSpaceEntry() {
        super(new PackageItemInfo(/* packageName= */ "", Process.myUserHandle()),
                /* titleSectionName= */ "",
                Collections.EMPTY_LIST);
        mPkgItem.title = "";
    }
}
