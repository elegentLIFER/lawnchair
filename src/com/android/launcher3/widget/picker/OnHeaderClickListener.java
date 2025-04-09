
package com.android.launcher3.widget.picker;

import com.android.launcher3.util.PackageUserKey;

/**
 * A listener to be invoked when a header is clicked.
 */
public interface OnHeaderClickListener {
    /**
     * Calls when a header is clicked to show / hide widgets for a package.
     */
    void onHeaderClicked(boolean showWidgets, PackageUserKey key);
}
