
package com.android.launcher3.widget.picker;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;

import com.android.launcher3.PagedView;
import com.android.launcher3.workprofile.PersonalWorkPagedView;

/**
 * A {@link PagedView} for showing different widgets for the personal and work profile.
 */
public class WidgetPagedView extends PersonalWorkPagedView {

    public WidgetPagedView(Context context) {
        this(context, null);
    }

    public WidgetPagedView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WidgetPagedView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setPageSpacing(getPaddingLeft());
    }

    @Override
    public void getDrawingRect(Rect outRect) {
        super.getDrawingRect(outRect);
        outRect.left += getPaddingLeft();
        outRect.right -= getPaddingRight();
    }
}
