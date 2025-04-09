
package com.android.quickstep.inputconsumers;

import android.view.MotionEvent;

import com.android.launcher3.taskbar.TaskbarActivityContext;
import com.android.quickstep.InputConsumer;
import com.android.quickstep.TaskAnimationManager;

import java.util.function.Supplier;

/**
 * A NO_OP input consumer which also resets any pending gesture
 */
public class ResetGestureInputConsumer implements InputConsumer {

    private final TaskAnimationManager mTaskAnimationManager;
    private final Supplier<TaskbarActivityContext> mActivityContextSupplier;

    public ResetGestureInputConsumer(
            TaskAnimationManager taskAnimationManager,
            Supplier<TaskbarActivityContext> activityContextSupplier) {
        mTaskAnimationManager = taskAnimationManager;
        mActivityContextSupplier = activityContextSupplier;
    }

    @Override
    public int getType() {
        return TYPE_RESET_GESTURE;
    }

    @Override
    public void onMotionEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN
                && mTaskAnimationManager.isRecentsAnimationRunning()) {
            TaskbarActivityContext tac = mActivityContextSupplier.get();
            mTaskAnimationManager.finishRunningRecentsAnimation(
                    /* toHome= */ tac != null && !tac.isInApp());
        }
    }
}
