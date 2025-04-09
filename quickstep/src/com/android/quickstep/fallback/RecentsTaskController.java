
package com.android.quickstep.fallback;

import com.android.launcher3.uioverrides.touchcontrollers.TaskViewTouchController;
import com.android.quickstep.RecentsActivity;

public class RecentsTaskController extends TaskViewTouchController<RecentsActivity> {

    public RecentsTaskController(RecentsActivity activity) {
        super(activity);
    }

    @Override
    protected boolean isRecentsInteractive() {
        return mContainer.hasWindowFocus() || mContainer.getStateManager().getState().hasLiveTile();
    }

    @Override
    protected boolean isRecentsModal() {
        return false;
    }
}
