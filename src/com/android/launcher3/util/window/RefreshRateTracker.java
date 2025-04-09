
package com.android.launcher3.util.window;

import static android.view.Display.DEFAULT_DISPLAY;

import static com.android.launcher3.util.Executors.UI_HELPER_EXECUTOR;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManager.DisplayListener;
import android.view.Display;

import androidx.annotation.WorkerThread;

import com.android.launcher3.util.MainThreadInitializedObject;
import com.android.launcher3.util.SafeCloseable;

/**
 * Utility class to track refresh rate of the current device
 */
public class RefreshRateTracker implements DisplayListener, SafeCloseable {

    private static final MainThreadInitializedObject<RefreshRateTracker> INSTANCE =
            new MainThreadInitializedObject<>(RefreshRateTracker::new);

    private int mSingleFrameMs = 1;

    private final DisplayManager mDM;

    private RefreshRateTracker(Context context) {
        mDM = context.getSystemService(DisplayManager.class);
        updateSingleFrameMs();
        mDM.registerDisplayListener(this, UI_HELPER_EXECUTOR.getHandler());
    }

    /**
     * Returns the single frame time in ms
     */
    public static int getSingleFrameMs(Context context) {
        return INSTANCE.get(context).mSingleFrameMs;
    }

    @Override
    public final void onDisplayAdded(int displayId) { }

    @Override
    public final void onDisplayRemoved(int displayId) { }

    @WorkerThread
    @Override
    public final void onDisplayChanged(int displayId) {
        if (displayId == DEFAULT_DISPLAY) {
            updateSingleFrameMs();
        }
    }

    private void updateSingleFrameMs() {
        Display display = mDM.getDisplay(DEFAULT_DISPLAY);
        if (display != null) {
            float refreshRate = display.getRefreshRate();
            mSingleFrameMs = refreshRate > 0 ? (int) (1000 / refreshRate) : 16;
        }
    }

    @Override
    public void close() {
        mDM.unregisterDisplayListener(this);
    }
}
