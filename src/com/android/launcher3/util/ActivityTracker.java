
package com.android.launcher3.util;

import android.util.Log;

import androidx.annotation.Nullable;

import com.android.launcher3.BaseActivity;

import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Helper class to statically track activity creation
 * @param <T> The activity type to track
 */
public final class ActivityTracker<T extends BaseActivity> {

    private static final String TAG = "ActivityTracker";

    private WeakReference<T> mCurrentActivity = new WeakReference<>(null);
    private CopyOnWriteArrayList<SchedulerCallback<T>> mCallbacks = new CopyOnWriteArrayList<>();

    @Nullable
    public <R extends T> R getCreatedActivity() {
        return (R) mCurrentActivity.get();
    }

    public void onActivityDestroyed(T activity) {
        if (mCurrentActivity.get() == activity) {
            mCurrentActivity.clear();
        }
    }

    /**
     * Call {@link SchedulerCallback#init(BaseActivity, boolean)} when the
     * activity is ready. If the activity is already created, this is called immediately.
     *
     * The tracker maintains a strong ref to the callback, so it is up to the caller to return
     * {@code false} in the callback OR to unregister the callback explicitly.
     *
     * @param callback The callback to call init() on when the activity is ready.
     */
    public void registerCallback(SchedulerCallback<T> callback, String reasonString) {
        Log.d(TAG, "Registering callback: " + callback + ", reason=" + reasonString);
        T activity = mCurrentActivity.get();
        mCallbacks.add(callback);
        if (activity != null) {
            if (!callback.init(activity, activity.isStarted())) {
                unregisterCallback(callback, "ActivityTracker.registerCallback: Intent handled");
            }
        }
    }

    /**
     * Unregisters a registered callback.
     */
    public void unregisterCallback(SchedulerCallback<T> callback, String reasonString) {
        Log.d(TAG, "Unregistering callback: " + callback + ", reason=" + reasonString);
        mCallbacks.remove(callback);
    }

    public boolean handleCreate(T activity) {
        mCurrentActivity = new WeakReference<>(activity);
        return handleIntent(activity, false /* alreadyOnHome */);
    }

    public boolean handleNewIntent(T activity) {
        return handleIntent(activity, activity.isStarted());
    }

    private boolean handleIntent(T activity, boolean alreadyOnHome) {
        boolean handled = false;
        if (!mCallbacks.isEmpty()) {
            Log.d(TAG, "handleIntent: mCallbacks=" + mCallbacks);
        }
        for (SchedulerCallback<T> cb : mCallbacks) {
            if (!cb.init(activity, alreadyOnHome)) {
                // Callback doesn't want any more updates
                unregisterCallback(cb, "ActivityTracker.handleIntent: Intent handled");
            }
            handled = true;
        }
        return handled;
    }

    public void dump(String prefix, PrintWriter writer) {
        writer.println(prefix + "ActivityTracker:");
        writer.println(prefix + "\tmCurrentActivity=" + mCurrentActivity.get());
        writer.println(prefix + "\tmCallbacks=" + mCallbacks);
    }

    public interface SchedulerCallback<T extends BaseActivity> {

        /**
         * Called when the activity is ready.
         * @param alreadyOnHome Whether the activity is already started.
         * @return Whether to continue receiving callbacks (i.e. if the activity is recreated).
         */
        boolean init(T activity, boolean alreadyOnHome);
    }
}
