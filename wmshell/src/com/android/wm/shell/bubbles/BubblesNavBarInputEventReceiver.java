

package com.android.wm.shell.bubbles;

import android.os.Looper;
import android.view.BatchedInputEventReceiver;
import android.view.Choreographer;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.MotionEvent;

/**
 * Bubbles {@link BatchedInputEventReceiver} for monitoring touches from navbar gesture area
 */
class BubblesNavBarInputEventReceiver extends BatchedInputEventReceiver {

    private final BubblesNavBarMotionEventHandler mMotionEventHandler;

    BubblesNavBarInputEventReceiver(InputChannel inputChannel,
            Choreographer choreographer, BubblesNavBarMotionEventHandler motionEventHandler) {
        super(inputChannel, Looper.myLooper(), choreographer);
        mMotionEventHandler = motionEventHandler;
    }

    @Override
    public void onInputEvent(InputEvent event) {
        boolean handled = false;
        try {
            if (!(event instanceof MotionEvent)) {
                return;
            }
            handled = mMotionEventHandler.onMotionEvent((MotionEvent) event);
        } finally {
            finishInputEvent(event, handled);
        }
    }
}
