

package com.android.launcher3.util;

import com.android.launcher3.logging.StatsLogManager;
import com.android.launcher3.util.SplitConfigurationOptions.StagePosition;

/**
 * Utility class to store information regarding a split select request. This includes the taskId of
 * the originating task, plus the stage position.
 * This information is intended to be saved across launcher instances, e.g. when Launcher needs to
 * recover straight into a split select state.
 */
public class PendingSplitSelectInfo {

    private final int mStagedTaskId;
    private final int mStagePosition;
    private final StatsLogManager.EventEnum mSource;

    public PendingSplitSelectInfo(int stagedTaskId, int stagePosition,
            StatsLogManager.EventEnum source) {
        this.mStagedTaskId = stagedTaskId;
        this.mStagePosition = stagePosition;
        this.mSource = source;
    }

    public int getStagedTaskId() {
        return mStagedTaskId;
    }

    public @StagePosition int getStagePosition() {
        return mStagePosition;
    }

    public StatsLogManager.EventEnum getSource() {
        return mSource;
    }
}
