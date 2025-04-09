
package com.android.launcher3.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.android.launcher3.AbstractFloatingView;

/**
 * An invisible AbstractFloatingView that can run a callback when it is being closed.
 */
public class ListenerView extends AbstractFloatingView {

    private Runnable mCloseListener;

    public ListenerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setVisibility(View.GONE);
    }

    public void setListener(Runnable listener) {
        mCloseListener = listener;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mIsOpen = true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mIsOpen = false;
    }

    @Override
    protected void handleClose(boolean animate) {
        if (mIsOpen) {
            if (mCloseListener != null) {
                mCloseListener.run();
            } else {
                if (getParent() instanceof ViewGroup) {
                    ((ViewGroup) getParent()).removeView(this);
                }
            }
        }
        mIsOpen = false;
    }

    @Override
    protected boolean isOfType(int type) {
        return (type & TYPE_LISTENER) != 0;
    }

    @Override
    public boolean onControllerInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            handleClose(false);
        }
        // We want other views to be able to intercept the touch so we return false here.
        return false;
    }

    @Override
    public boolean canInterceptEventsInSystemGestureRegion() {
        return true;
    }
}
