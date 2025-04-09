

package com.android.wm.shell.bubbles;

import static com.android.wm.shell.bubbles.BubbleDebugConfig.TAG_BUBBLES;
import static com.android.wm.shell.bubbles.BubbleDebugConfig.TAG_WITH_CLASS_NAME;
import static com.android.wm.shell.protolog.ShellProtoLogGroup.WM_SHELL_BUBBLES;

import android.content.Context;
import android.hardware.input.InputManager;
import android.view.Choreographer;
import android.view.InputChannel;
import android.view.InputEventReceiver;
import android.view.InputMonitor;

import androidx.annotation.Nullable;

import com.android.internal.protolog.common.ProtoLog;
import com.android.wm.shell.bubbles.BubblesNavBarMotionEventHandler.MotionEventListener;

/**
 * Set up tracking bubbles gestures that begin in navigation bar
 */
class BubblesNavBarGestureTracker {

    private static final String TAG = TAG_WITH_CLASS_NAME ? "BubblesGestureTracker" : TAG_BUBBLES;

    private static final String GESTURE_MONITOR = "bubbles-gesture";
    private final Context mContext;
    private final BubblePositioner mPositioner;

    @Nullable
    private InputMonitor mInputMonitor;
    @Nullable
    private InputEventReceiver mInputEventReceiver;

    BubblesNavBarGestureTracker(Context context, BubblePositioner positioner) {
        mContext = context;
        mPositioner = positioner;
    }

    /**
     * Start tracking gestures
     *
     * @param listener listener that is notified of touch events
     */
    void start(MotionEventListener listener) {
        ProtoLog.d(WM_SHELL_BUBBLES, "start monitoring bubbles swipe up gesture");

        stopInternal();

        mInputMonitor = mContext.getSystemService(InputManager.class)
                .monitorGestureInput(GESTURE_MONITOR, mContext.getDisplayId());
        InputChannel inputChannel = mInputMonitor.getInputChannel();

        BubblesNavBarMotionEventHandler motionEventHandler =
                new BubblesNavBarMotionEventHandler(mContext, mPositioner,
                        this::onInterceptTouch, listener);
        mInputEventReceiver = new BubblesNavBarInputEventReceiver(inputChannel,
                Choreographer.getInstance(), motionEventHandler);
    }

    void stop() {
        ProtoLog.d(WM_SHELL_BUBBLES, "stop monitoring bubbles swipe up gesture");
        stopInternal();
    }

    private void stopInternal() {
        if (mInputEventReceiver != null) {
            mInputEventReceiver.dispose();
            mInputEventReceiver = null;
        }
        if (mInputMonitor != null) {
            mInputMonitor.dispose();
            mInputMonitor = null;
        }
    }

    private void onInterceptTouch() {
        ProtoLog.d(WM_SHELL_BUBBLES, "intercept touch event");
        if (mInputMonitor != null) {
            mInputMonitor.pilferPointers();
        }
    }
}
