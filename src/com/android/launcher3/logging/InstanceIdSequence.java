

package com.android.launcher3.logging;

import static java.lang.Math.max;
import static java.lang.Math.min;

import androidx.annotation.VisibleForTesting;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Generates random InstanceIds in range [1, instanceIdMax] for passing to
 * UiEventLogger.logWithInstanceId(). Holds a SecureRandom, which self-seeds on first use; try to
 * give it a long lifetime. Safe for concurrent use.
 *
 * Copy of frameworks/base/core/java/com/android/internal/logging/InstanceIdSequence.java
 */
public class InstanceIdSequence {
    protected final int mInstanceIdMax;
    private final Random mRandom = new SecureRandom();

    /**
     * Constructs a sequence with identifiers [1, instanceIdMax].  Capped at INSTANCE_ID_MAX.
     * @param instanceIdMax Limiting value of identifiers. Normally positive: otherwise you get
     *                      an all-1 sequence.
     */
    public InstanceIdSequence(int instanceIdMax) {
        mInstanceIdMax = min(max(1, instanceIdMax), InstanceId.INSTANCE_ID_MAX);
    }

    /**
     * Constructs a sequence with identifiers [1, InstanceId.INSTANCE_ID_MAX].
     */
    public InstanceIdSequence() {
        this(InstanceId.INSTANCE_ID_MAX);
    }

    /**
     * Gets the next instance from the sequence.  Safe for concurrent use.
     * @return new InstanceId
     */
    public InstanceId newInstanceId() {
        return newInstanceIdInternal(1 + mRandom.nextInt(mInstanceIdMax));
    }

    /**
     * Factory function for instance IDs, used for testing.
     * @param id
     * @return new InstanceId(id)
     */
    @VisibleForTesting
    protected InstanceId newInstanceIdInternal(int id) {
        return new InstanceId(id);
    }
}
