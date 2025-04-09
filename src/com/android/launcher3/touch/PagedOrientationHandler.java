

package com.android.launcher3.touch;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

/**
 * Abstraction layer to separate horizontal and vertical specific implementations
 * for {@link com.android.launcher3.PagedView}. Majority of these implementations are (should be) as
 * simple as choosing the correct X and Y analogous methods.
 */
public interface PagedOrientationHandler {

    PagedOrientationHandler DEFAULT = new DefaultPagedViewHandler();

    interface Int2DAction<T> {
        void call(T target, int x, int y);
    }
    interface Float2DAction<T> {
        void call(T target, float x, float y);
    }
    Int2DAction<View> VIEW_SCROLL_BY = View::scrollBy;
    Int2DAction<View> VIEW_SCROLL_TO = View::scrollTo;
    Float2DAction<Canvas> CANVAS_TRANSLATE = Canvas::translate;
    Float2DAction<Matrix> MATRIX_POST_TRANSLATE = Matrix::postTranslate;

    <T> void setPrimary(T target, Int2DAction<T> action, int param);
    <T> void setPrimary(T target, Float2DAction<T> action, float param);
    float getPrimaryDirection(MotionEvent event, int pointerIndex);
    float getPrimaryVelocity(VelocityTracker velocityTracker, int pointerId);
    int getMeasuredSize(View view);
    int getPrimaryScroll(View view);
    float getPrimaryScale(View view);
    int getChildStart(View view);
    int getCenterForPage(View view, Rect insets);
    int getScrollOffsetStart(View view, Rect insets);
    int getScrollOffsetEnd(View view, Rect insets);
    ChildBounds getChildBounds(View child, int childStart, int pageCenter, boolean layoutChild);
    void setMaxScroll(AccessibilityEvent event, int maxScroll);
    boolean getRecentsRtlSetting(Resources resources);

    int getPrimaryValue(int x, int y);
    int getSecondaryValue(int x, int y);

    float getPrimaryValue(float x, float y);
    float getSecondaryValue(float x, float y);

    class ChildBounds {

        public final int primaryDimension;
        public final int secondaryDimension;
        public final int childPrimaryEnd;
        public final int childSecondaryEnd;

        public ChildBounds(int primaryDimension, int secondaryDimension, int childPrimaryEnd,
                int childSecondaryEnd) {
            this.primaryDimension = primaryDimension;
            this.secondaryDimension = secondaryDimension;
            this.childPrimaryEnd = childPrimaryEnd;
            this.childSecondaryEnd = childSecondaryEnd;
        }
    }
}
