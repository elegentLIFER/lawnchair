
package com.android.quickstep.inputconsumers;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;

import android.content.Context;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.android.quickstep.InputConsumer;
import com.android.quickstep.SystemUiProxy;
import com.android.systemui.shared.system.InputMonitorCompat;

/** Allows the status bar to be pull down for notification shade using the trackpad. */
public class TrackpadStatusBarInputConsumer extends DelegateInputConsumer {

    private final SystemUiProxy mSystemUiProxy;
    private final float mTouchSlop;
    private final PointF mDown = new PointF();
    private boolean mHasPassedTouchSlop;

    public TrackpadStatusBarInputConsumer(Context context, InputConsumer delegate,
            InputMonitorCompat inputMonitor) {
        super(delegate, inputMonitor);

        mSystemUiProxy = SystemUiProxy.INSTANCE.get(context);
        mTouchSlop = 2 * ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public int getType() {
        return TYPE_STATUS_BAR | mDelegate.getType();
    }

    @Override
    public void onMotionEvent(MotionEvent ev) {
        if (mState != STATE_ACTIVE) {
            mDelegate.onMotionEvent(ev);

            switch (ev.getActionMasked()) {
                case ACTION_DOWN -> {
                    mDown.set(ev.getX(), ev.getY());
                    mHasPassedTouchSlop = false;
                }
                case ACTION_MOVE -> {
                    if (!mHasPassedTouchSlop) {
                        float displacementY = ev.getY() - mDown.y;
                        if (Math.abs(displacementY) > mTouchSlop) {
                            mHasPassedTouchSlop = true;
                            if (displacementY > 0) {
                                setActive(ev);
                                ev.setAction(ACTION_DOWN);
                                dispatchTouchEvent(ev);
                            }
                        }
                    }
                }
            }
        } else {
            dispatchTouchEvent(ev);
        }
    }

    private void dispatchTouchEvent(MotionEvent ev) {
        if (mSystemUiProxy.isActive()) {
            mSystemUiProxy.onStatusBarTrackpadEvent(ev);
        }
    }

    @Override
    protected String getDelegatorName() {
        return "TrackpadStatusBarInputConsumer";
    }
}
