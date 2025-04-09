
package com.android.quickstep.util;

import com.android.launcher3.BaseActivity;
import com.android.launcher3.util.ActivityTracker;
import com.android.launcher3.util.ActivityTracker.SchedulerCallback;

import java.util.function.BiPredicate;

public class ActivityInitListener<T extends BaseActivity> implements
        SchedulerCallback<T> {

    private BiPredicate<T, Boolean> mOnInitListener;
    private final ActivityTracker<T> mActivityTracker;

    private boolean mIsRegistered = false;

    /**
     * @param onInitListener a callback made when the activity is initialized. The callback should
     *                       return true to continue receiving callbacks (ie. for if the activity is
     *                       recreated).
     */
    public ActivityInitListener(BiPredicate<T, Boolean> onInitListener,
            ActivityTracker<T> tracker) {
        mOnInitListener = onInitListener;
        mActivityTracker = tracker;
    }

    @Override
    public final boolean init(T activity, boolean alreadyOnHome) {
        if (!mIsRegistered) {
            // Don't receive any more updates
            return false;
        }
        return handleInit(activity, alreadyOnHome);
    }

    protected boolean handleInit(T activity, boolean alreadyOnHome) {
        return mOnInitListener.test(activity, alreadyOnHome);
    }

    /**
     * Registers the activity-created listener. If the activity is already created, then the
     * callback provided in the constructor will be called synchronously.
     */
    public void register(String reasonString) {
        mIsRegistered = true;
        mActivityTracker.registerCallback(this, reasonString);
    }

    /**
     * After calling this, we won't {@link #init} even when the activity is ready.
     */
    public void unregister(String reasonString) {
        mActivityTracker.unregisterCallback(this, reasonString);
        mIsRegistered = false;
        mOnInitListener = null;
    }
}
