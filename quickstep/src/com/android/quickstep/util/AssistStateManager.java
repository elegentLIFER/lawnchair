
package com.android.quickstep.util;

import static com.android.launcher3.util.MainThreadInitializedObject.forOverride;

import com.android.launcher3.R;
import com.android.launcher3.util.MainThreadInitializedObject;
import com.android.launcher3.util.ResourceBasedOverride;
import com.android.launcher3.util.SafeCloseable;

import java.io.PrintWriter;
import java.util.Optional;

/** Class to manage Assistant states. */
public class AssistStateManager implements ResourceBasedOverride, SafeCloseable {

    public static final MainThreadInitializedObject<AssistStateManager> INSTANCE =
            forOverride(AssistStateManager.class, R.string.assist_state_manager_class);

    public AssistStateManager() {}

    /** Return {@code true} if the Settings toggle is enabled. */
    public boolean isSettingsAllEntrypointsEnabled() {
        return false;
    }

    /** Whether search supports showing on the lockscreen. */
    public boolean supportsShowWhenLocked() {
        return false;
    }

    /** Whether ContextualSearchService invocation path is available. */
    public boolean isContextualSearchServiceAvailable() {
        return false;
    }

    /** Get the Launcher overridden long press nav handle duration to trigger Assistant. */
    public Optional<Long> getLPNHDurationMillis() {
        return Optional.empty();
    }

    /**
     * Get the Launcher overridden long press nav handle touch slop multiplier to trigger Assistant.
     */
    public Optional<Float> getLPNHCustomSlopMultiplier() {
        return Optional.empty();
    }

    /** Get the Launcher overridden long press home duration to trigger Assistant. */
    public Optional<Long> getLPHDurationMillis() {
        return Optional.empty();
    }

    /** Get the Launcher overridden long press home touch slop multiplier to trigger Assistant. */
    public Optional<Float> getLPHCustomSlopMultiplier() {
        return Optional.empty();
    }

    /** Get the long press duration data source. */
    public int getDurationDataSource() {
        return 0;
    }

    /** Get the long press touch slop multiplier data source. */
    public int getSlopDataSource() {
        return 0;
    }

    /** Get the haptic bit overridden by AGSA. */
    public Optional<Boolean> getShouldPlayHapticOverride() {
        return Optional.empty();
    }

    /** Dump states. */
    public void dump(String prefix, PrintWriter writer) {}

    @Override
    public void close() {}
}
