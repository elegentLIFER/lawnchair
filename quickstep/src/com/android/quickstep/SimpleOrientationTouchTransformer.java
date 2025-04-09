
package com.android.quickstep;

import static com.android.launcher3.util.DisplayController.CHANGE_ACTIVE_SCREEN;
import static com.android.launcher3.util.DisplayController.CHANGE_ALL;
import static com.android.launcher3.util.DisplayController.CHANGE_ROTATION;

import android.content.Context;
import android.view.MotionEvent;

import com.android.launcher3.util.DisplayController;
import com.android.launcher3.util.MainThreadInitializedObject;
import com.android.launcher3.util.SafeCloseable;

public class SimpleOrientationTouchTransformer implements
        DisplayController.DisplayInfoChangeListener, SafeCloseable {

    public static final MainThreadInitializedObject<SimpleOrientationTouchTransformer> INSTANCE =
            new MainThreadInitializedObject<>(SimpleOrientationTouchTransformer::new);

    private final Context mContext;
    private OrientationRectF mOrientationRectF;

    public SimpleOrientationTouchTransformer(Context context) {
        mContext = context;
        DisplayController.INSTANCE.get(context).addChangeListener(this);
        onDisplayInfoChanged(context, DisplayController.INSTANCE.get(context).getInfo(),
                CHANGE_ALL);
    }

    @Override
    public void close() {
        DisplayController.INSTANCE.get(mContext).removeChangeListener(this);
    }

    @Override
    public void onDisplayInfoChanged(Context context, DisplayController.Info info, int flags) {
        if ((flags & (CHANGE_ROTATION | CHANGE_ACTIVE_SCREEN)) == 0) {
            return;
        }
        mOrientationRectF = new OrientationRectF(0, 0, info.currentSize.y, info.currentSize.x,
                info.rotation);
    }

    public void transform(MotionEvent ev, int rotation) {
        mOrientationRectF.applyTransformToRotation(ev, rotation, true /* forceTransform */);
    }
}
