

package com.android.launcher3;

import android.view.View;

import com.android.launcher3.DropTarget.DragObject;

/**
 * Interface defining an object that can originate a drag.
 */
public interface DragSource {

    /**
     * A callback made back to the source after an item from this source has been dropped on a
     * DropTarget.
     */
    void onDropCompleted(View target, DragObject d, boolean success);
}
