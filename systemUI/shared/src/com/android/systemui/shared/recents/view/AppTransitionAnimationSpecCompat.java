
package com.android.systemui.shared.recents.view;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.AppTransitionAnimationSpec;

/**
 * Wraps the internal app transition animation spec.
 */
public class AppTransitionAnimationSpecCompat {

    private int mTaskId;
    private Bitmap mBuffer;
    private Rect mRect;

    public AppTransitionAnimationSpecCompat(int taskId, Bitmap buffer, Rect rect) {
        mTaskId = taskId;
        mBuffer = buffer;
        mRect = rect;
    }

    public AppTransitionAnimationSpec toAppTransitionAnimationSpec() {
        return new AppTransitionAnimationSpec(mTaskId,
                mBuffer != null ? mBuffer.getHardwareBuffer() : null, mRect);
    }
}
