

package com.android.launcher3.tapl;

import android.graphics.Rect;

public interface IconDragTarget {

    /** This method requires public access, however should not be called in tests. */
    Rect getDropLocationBounds();

    /** This method requires public access, however should not be called in tests. */
    FolderIcon getTargetIcon(Rect bounds);
}
