
package com.android.launcher3.touch;

import android.content.Context;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import androidx.annotation.NonNull;

import com.android.launcher3.Utilities;

/**
 * Two dimensional scroll/drag/swipe gesture detector that reports x and y displacement/velocity.
 */
public class BothAxesSwipeDetector extends BaseSwipeDetector {

    public static final int DIRECTION_UP = 1 << 0;
    // Note that this will track left instead of right in RTL.
    public static final int DIRECTION_RIGHT = 1 << 1;
    public static final int DIRECTION_DOWN = 1 << 2;
    // Note that this will track right instead of left in RTL.
    public static final int DIRECTION_LEFT = 1 << 3;

    /* Client of this gesture detector can register a callback. */
    private final Listener mListener;

    private int mScrollDirections;

    public BothAxesSwipeDetector(@NonNull Context context, @NonNull Listener l) {
        super(context, ViewConfiguration.get(context), Utilities.isRtl(context.getResources()));
        mListener = l;
    }

    public void setDetectableScrollConditions(int scrollDirectionFlags, boolean ignoreSlop) {
        mScrollDirections = scrollDirectionFlags;
        mIgnoreSlopWhenSettling = ignoreSlop;
    }

    @Override
    protected boolean shouldScrollStart(PointF displacement) {
        // Check if the client is interested in scroll in current direction.
        boolean canScrollUp = (mScrollDirections & DIRECTION_UP) > 0
                && displacement.y <= -mTouchSlop;
        boolean canScrollRight = (mScrollDirections & DIRECTION_RIGHT) > 0
                && displacement.x >= mTouchSlop;
        boolean canScrollDown = (mScrollDirections & DIRECTION_DOWN) > 0
                && displacement.y >= mTouchSlop;
        boolean canScrollLeft = (mScrollDirections & DIRECTION_LEFT) > 0
                && displacement.x <= -mTouchSlop;
        return canScrollUp || canScrollRight || canScrollDown || canScrollLeft;
    }

    @Override
    protected void reportDragStartInternal(boolean recatch) {
        mListener.onDragStart(!recatch);
    }

    @Override
    protected void reportDraggingInternal(PointF displacement, MotionEvent event) {
        mListener.onDrag(displacement, event);
    }

    @Override
    protected void reportDragEndInternal(PointF velocity) {
        mListener.onDragEnd(velocity);
    }

    /** Listener to receive updates on the swipe. */
    public interface Listener {
        /** @param start whether this was the original drag start, as opposed to a recatch. */
        void onDragStart(boolean start);

        boolean onDrag(PointF displacement, MotionEvent motionEvent);

        void onDragEnd(PointF velocity);
    }
}
