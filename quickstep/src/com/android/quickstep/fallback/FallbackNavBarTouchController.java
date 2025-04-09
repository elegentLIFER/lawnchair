
package com.android.quickstep.fallback;

import android.graphics.PointF;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import com.android.launcher3.Utilities;
import com.android.launcher3.util.DisplayController;
import com.android.launcher3.util.NavigationMode;
import com.android.launcher3.util.TouchController;
import com.android.quickstep.util.NavBarPosition;
import com.android.quickstep.util.TriggerSwipeUpTouchTracker;
import com.android.quickstep.views.RecentsViewContainer;

/**
 * In 0-button mode, intercepts swipe up from the nav bar on FallbackRecentsView to go home.
 */
public class FallbackNavBarTouchController implements TouchController,
        TriggerSwipeUpTouchTracker.OnSwipeUpListener {

    private final RecentsViewContainer mContainer;
    @Nullable
    private final TriggerSwipeUpTouchTracker mTriggerSwipeUpTracker;

    public FallbackNavBarTouchController(RecentsViewContainer container) {
        mContainer = container;
        NavigationMode sysUINavigationMode =
                DisplayController.getNavigationMode(mContainer.asContext());
        if (sysUINavigationMode == NavigationMode.NO_BUTTON) {
            NavBarPosition navBarPosition = new NavBarPosition(sysUINavigationMode,
                    DisplayController.INSTANCE.get(mContainer.asContext()).getInfo());
            mTriggerSwipeUpTracker = new TriggerSwipeUpTouchTracker(mContainer.asContext(),
                    true /* disableHorizontalSwipe */, navBarPosition, this);
        } else {
            mTriggerSwipeUpTracker = null;
        }
    }

    @Override
    public boolean onControllerInterceptTouchEvent(MotionEvent ev) {
        boolean cameFromNavBar = (ev.getEdgeFlags() & Utilities.EDGE_NAV_BAR) != 0;
        if (cameFromNavBar && mTriggerSwipeUpTracker != null) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                mTriggerSwipeUpTracker.init();
            }
            onControllerTouchEvent(ev);
            return mTriggerSwipeUpTracker.interceptedTouch();
        }
        return false;
    }

    @Override
    public boolean onControllerTouchEvent(MotionEvent ev) {
        if (mTriggerSwipeUpTracker != null) {
            mTriggerSwipeUpTracker.onMotionEvent(ev);
            return true;
        }
        return false;
    }

    @Override
    public void onSwipeUp(boolean wasFling, PointF finalVelocity) {
        mContainer.<FallbackRecentsView>getOverviewPanel().startHome();
    }
}
