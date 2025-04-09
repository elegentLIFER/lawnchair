
package com.android.quickstep.inputconsumers;

import android.content.Context;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;

import com.android.launcher3.R;
import com.android.quickstep.GestureState;
import com.android.quickstep.InputConsumer;
import com.android.quickstep.SystemUiProxy;
import com.android.quickstep.util.MotionPauseDetector;
import com.android.quickstep.views.RecentsViewContainer;

/**
 * An input consumer that detects swipe up and hold to exit screen pinning mode.
 */
public class ScreenPinnedInputConsumer implements InputConsumer {

    private static final String TAG = "ScreenPinnedConsumer";

    private final float mMotionPauseMinDisplacement;
    private final MotionPauseDetector mMotionPauseDetector;

    private float mTouchDownY;

    public ScreenPinnedInputConsumer(Context context, GestureState gestureState) {
        mMotionPauseMinDisplacement = context.getResources().getDimension(
                R.dimen.motion_pause_detector_min_displacement_from_app);
        mMotionPauseDetector = new MotionPauseDetector(context, true /* makePauseHarderToTrigger*/);
        mMotionPauseDetector.setOnMotionPauseListener(() -> {
            SystemUiProxy.INSTANCE.get(context).stopScreenPinning();
            RecentsViewContainer container = gestureState.getContainerInterface()
                    .getCreatedContainer();
            if (container != null) {
                container.getRootView().performHapticFeedback(
                        HapticFeedbackConstants.LONG_PRESS,
                        HapticFeedbackConstants.FLAG_IGNORE_VIEW_SETTING);
            }
            mMotionPauseDetector.clear();
        });
    }

    @Override
    public int getType() {
        return TYPE_SCREEN_PINNED;
    }

    @Override
    public void onMotionEvent(MotionEvent ev) {
        float y = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchDownY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float displacement = mTouchDownY - y;
                mMotionPauseDetector.setDisallowPause(displacement < mMotionPauseMinDisplacement);
                mMotionPauseDetector.addPosition(ev);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mMotionPauseDetector.clear();
                break;
        }
    }
}
