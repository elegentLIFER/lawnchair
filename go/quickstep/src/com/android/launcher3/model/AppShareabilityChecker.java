

package com.android.launcher3.model;

import androidx.annotation.Nullable;

import java.util.List;
import java.util.function.Consumer;

/**
 * Interface for checking apps' shareability. Implementations need to be able to determine whether
 * apps are shareable given their package names.
 */
public interface AppShareabilityChecker {
    /**
     * Checks the shareability of the provided apps. Once the check is complete, updates the
     * provided manager with the results and calls the (optionally) provided callback.
     * @param packageNames The apps to check
     * @param shareMgr The manager to receive the results
     * @param callback Optional callback to be invoked when the check is finished
     */
    void checkApps(List<String> packageNames, AppShareabilityManager shareMgr,
            @Nullable Consumer<Boolean> callback);
}
