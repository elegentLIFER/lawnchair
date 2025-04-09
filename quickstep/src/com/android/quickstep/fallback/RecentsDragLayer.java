
package com.android.quickstep.fallback;

import android.content.Context;
import android.util.AttributeSet;

import com.android.launcher3.util.TouchController;
import com.android.launcher3.views.BaseDragLayer;
import com.android.quickstep.RecentsActivity;

/**
 * Drag layer for fallback recents activity
 */
public class RecentsDragLayer extends BaseDragLayer<RecentsActivity> {

    public RecentsDragLayer(Context context, AttributeSet attrs) {
        super(context, attrs, 1 /* alphaChannelCount */);
    }

    @Override
    public void recreateControllers() {
        mControllers = new TouchController[] {
                new RecentsTaskController(mActivity),
                new FallbackNavBarTouchController(mActivity),
        };
    }
}
