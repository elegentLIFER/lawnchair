

package com.android.launcher3.util;

import static com.android.launcher3.util.Executors.MODEL_EXECUTOR;

import android.os.Looper;

import com.android.launcher3.config.FeatureFlags;

/**
 * A set of utility methods for thread verification.
 */
public class Preconditions {

    public static void assertNotNull(Object o) {
        if (FeatureFlags.IS_STUDIO_BUILD && o == null) {
            throw new IllegalStateException();
        }
    }

    public static void assertWorkerThread() {
        if (FeatureFlags.IS_STUDIO_BUILD && !isSameLooper(MODEL_EXECUTOR.getLooper())) {
            throw new IllegalStateException();
        }
    }

    public static void assertUIThread() {
        if (FeatureFlags.IS_STUDIO_BUILD && !isSameLooper(Looper.getMainLooper())) {
            throw new IllegalStateException();
        }
    }

    public static void assertNonUiThread() {
        if (FeatureFlags.IS_STUDIO_BUILD && isSameLooper(Looper.getMainLooper())) {
            throw new IllegalStateException();
        }
    }

    public static void assertTrue(boolean condition) {
        if (FeatureFlags.IS_STUDIO_BUILD && !condition) {
            throw new IllegalStateException();
        }
    }

    private static boolean isSameLooper(Looper looper) {
        return Looper.myLooper() == looper;
    }
}
