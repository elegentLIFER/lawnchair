

package com.android.launcher3.dragndrop;

import android.graphics.Rect;

import androidx.annotation.NonNull;

import com.android.launcher3.util.SafeCloseable;

/**
 * Interface defining methods required for drawing and previewing DragViews, drag previews, and
 * related animations
 */
public interface DraggableView {
    int DRAGGABLE_ICON = 0;
    int DRAGGABLE_WIDGET = 1;

    /**
     * Static ctr for a simple instance which just returns the view type.
     */
    static DraggableView ofType(int type) {
        return () -> type;
    }

    /**
     * Certain handling of DragViews depend only on whether this is an Icon Type item or a Widget
     * Type item.
     *
     * @return DRAGGABLE_ICON or DRAGGABLE_WIDGET as appropriate
     */
    int getViewType();

    /**
     * Before rendering as a DragView bitmap, some views need a preparation step. Returns a
     * callback to clear any preparation work
     */
    @NonNull default SafeCloseable prepareDrawDragView() {
        return () -> { };
    }

    /**
     * If an actual View subclass, this method returns the rectangle (within the View's coordinates)
     * of the visual region that should get dragged. This is used to extract exactly that element
     * as well as to offset that element as appropriate for various animations
     *
     * @param bounds Visual bounds in the views coordinates will be written here.
     */
    default void getWorkspaceVisualDragBounds(Rect bounds) { }

    /**
     * Same as above, but accounts for differing icon sizes between source and destination
     *
     * @param bounds Visual bounds in the views coordinates will be written here.
     */
    default void getSourceVisualDragBounds(Rect bounds) {
        getWorkspaceVisualDragBounds(bounds);
    }
}
