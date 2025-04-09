

package com.android.launcher3.dragndrop;

import android.content.Context;
import android.util.AttributeSet;

import com.android.launcher3.util.TouchController;
import com.android.launcher3.views.ActivityContext;
import com.android.launcher3.views.BaseDragLayer;

/**
 * A concrete {@link BaseDragLayer} that creates an empty list of {@link TouchController}s.
 * @param <T> The {@link ActivityContext} hosting the drag layer.
 */
public class SimpleDragLayer<T extends Context & ActivityContext> extends BaseDragLayer<T> {

    public SimpleDragLayer(Context context, AttributeSet attrs) {
        this(context, attrs, /*alphaChannelCount= */ 1);
    }

    public SimpleDragLayer(Context context, AttributeSet attrs, int alphaChannelCount) {
        super(context, attrs, alphaChannelCount);
    }

    @Override
    public void recreateControllers() {
        mControllers = new TouchController[] {};
    }
}
