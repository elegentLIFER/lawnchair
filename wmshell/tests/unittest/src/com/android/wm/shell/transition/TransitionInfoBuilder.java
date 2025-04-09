

package com.android.wm.shell.transition;

import static com.android.dx.mockito.inline.extended.ExtendedMockito.doReturn;

import static org.mockito.Mockito.mock;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.view.SurfaceControl;
import android.view.WindowManager;
import android.window.TransitionInfo;

/**
 * Utility for creating/editing synthetic TransitionInfos for tests.
 */
public class TransitionInfoBuilder {
    final TransitionInfo mInfo;
    static final int DISPLAY_ID = 0;

    public TransitionInfoBuilder(@WindowManager.TransitionType int type) {
        this(type, 0 /* flags */);
    }

    public TransitionInfoBuilder(@WindowManager.TransitionType int type,
            @WindowManager.TransitionFlags int flags) {
        this(type, flags, false /* asNoOp */);
    }

    public TransitionInfoBuilder(@WindowManager.TransitionType int type,
            @WindowManager.TransitionFlags int flags, boolean asNoOp) {
        mInfo = new TransitionInfo(type, flags);
        if (!asNoOp) {
            mInfo.addRootLeash(DISPLAY_ID, createMockSurface(true /* valid */), 0, 0);
        }
    }

    public TransitionInfoBuilder addChange(@WindowManager.TransitionType int mode,
            @TransitionInfo.ChangeFlags int flags, ActivityManager.RunningTaskInfo taskInfo,
            ComponentName activityComponent) {
        final TransitionInfo.Change change = new TransitionInfo.Change(
                taskInfo != null ? taskInfo.token : null, createMockSurface(true /* valid */));
        change.setMode(mode);
        change.setFlags(flags);
        change.setTaskInfo(taskInfo);
        change.setActivityComponent(activityComponent);
        return addChange(change);
    }

    /** Add a change to the TransitionInfo */
    public TransitionInfoBuilder addChange(@WindowManager.TransitionType int mode,
            @TransitionInfo.ChangeFlags int flags, ActivityManager.RunningTaskInfo taskInfo) {
        return addChange(mode, flags, taskInfo, null /* activityComponent */);
    }

    public TransitionInfoBuilder addChange(@WindowManager.TransitionType int mode,
            ActivityManager.RunningTaskInfo taskInfo) {
        return addChange(mode, TransitionInfo.FLAG_NONE, taskInfo);
    }

    /** Add a change to the TransitionInfo */
    public TransitionInfoBuilder addChange(@WindowManager.TransitionType int mode,
            ComponentName activityComponent) {
        return addChange(mode, TransitionInfo.FLAG_NONE, null /* taskinfo */, activityComponent);
    }

    public TransitionInfoBuilder addChange(@WindowManager.TransitionType int mode) {
        return addChange(mode, TransitionInfo.FLAG_NONE, null /* taskInfo */);
    }

    public TransitionInfoBuilder addChange(TransitionInfo.Change change) {
        change.setDisplayId(DISPLAY_ID, DISPLAY_ID);
        mInfo.addChange(change);
        return this;
    }

    public TransitionInfo build() {
        return mInfo;
    }

    private static SurfaceControl createMockSurface(boolean valid) {
        SurfaceControl sc = mock(SurfaceControl.class);
        doReturn(valid).when(sc).isValid();
        doReturn("TestSurface").when(sc).toString();
        return sc;
    }
}
