
package com.android.wm.shell.bubbles;

import static android.app.ActivityTaskManager.INVALID_TASK_ID;

import android.app.ActivityManager;
import android.os.IBinder;
import android.view.SurfaceControl;
import android.window.TransitionInfo;

import androidx.annotation.NonNull;

import com.android.wm.shell.shared.TransitionUtil;
import com.android.wm.shell.transition.Transitions;

/**
 * Observer used to identify tasks that are opening or moving to front. If a bubble activity is
 * currently opened when this happens, we'll collapse the bubbles.
 */
public class BubblesTransitionObserver implements Transitions.TransitionObserver {

    private BubbleController mBubbleController;
    private BubbleData mBubbleData;

    public BubblesTransitionObserver(BubbleController controller,
            BubbleData bubbleData) {
        mBubbleController = controller;
        mBubbleData = bubbleData;
    }

    @Override
    public void onTransitionReady(@NonNull IBinder transition, @NonNull TransitionInfo info,
            @NonNull SurfaceControl.Transaction startTransaction,
            @NonNull SurfaceControl.Transaction finishTransaction) {
        for (TransitionInfo.Change change : info.getChanges()) {
            final ActivityManager.RunningTaskInfo taskInfo = change.getTaskInfo();
            // We only care about opens / move to fronts when bubbles are expanded & not animating.
            if (taskInfo == null
                    || taskInfo.taskId == INVALID_TASK_ID
                    || !TransitionUtil.isOpeningType(change.getMode())
                    || mBubbleController.isStackAnimating()
                    || !mBubbleData.isExpanded()
                    || mBubbleData.getSelectedBubble() == null) {
                continue;
            }
            int expandedId = mBubbleData.getSelectedBubble().getTaskId();
            // If the task id that's opening is the same as the expanded bubble, skip collapsing
            // because it is our bubble that is opening.
            if (expandedId != INVALID_TASK_ID && expandedId != taskInfo.taskId) {
                mBubbleData.setExpanded(false);
            }
        }
    }

    @Override
    public void onTransitionStarting(@NonNull IBinder transition) {

    }

    @Override
    public void onTransitionMerged(@NonNull IBinder merged, @NonNull IBinder playing) {

    }

    @Override
    public void onTransitionFinished(@NonNull IBinder transition, boolean aborted) {

    }
}
