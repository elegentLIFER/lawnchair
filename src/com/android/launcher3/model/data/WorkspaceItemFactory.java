
package com.android.launcher3.model.data;

import android.content.Context;

/**
 * Interface to objects capable of generating workspace item
 */
public interface WorkspaceItemFactory {

    /**
     * Called to create a pinnable item info
     */
    WorkspaceItemInfo makeWorkspaceItem(Context context);
}
