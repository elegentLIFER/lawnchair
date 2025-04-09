

package com.android.wm.shell.windowdecor;

import static android.app.WindowConfiguration.WINDOWING_MODE_FREEFORM;
import static android.app.WindowConfiguration.WINDOWING_MODE_FULLSCREEN;
import static android.app.WindowConfiguration.WINDOWING_MODE_UNDEFINED;

import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.hardware.input.InputManager;
import android.os.SystemClock;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;

import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.freeform.FreeformTaskTransitionStarter;
import com.android.wm.shell.transition.Transitions;

/**
 * Utility class to handle task operations performed on a window decoration.
 */
class TaskOperations {
    private static final String TAG = "TaskOperations";

    private final FreeformTaskTransitionStarter mTransitionStarter;
    private final Context mContext;
    private final SyncTransactionQueue mSyncQueue;

    TaskOperations(FreeformTaskTransitionStarter transitionStarter, Context context,
            SyncTransactionQueue syncQueue) {
        mTransitionStarter = transitionStarter;
        mContext = context;
        mSyncQueue = syncQueue;
    }

    void injectBackKey(int displayId) {
        sendBackEvent(KeyEvent.ACTION_DOWN, displayId);
        sendBackEvent(KeyEvent.ACTION_UP, displayId);
    }

    private void sendBackEvent(int action, int displayId) {
        final long when = SystemClock.uptimeMillis();
        final KeyEvent ev = new KeyEvent(when, when, action, KeyEvent.KEYCODE_BACK,
                0 /* repeat */, 0 /* metaState */, KeyCharacterMap.VIRTUAL_KEYBOARD,
                0 /* scancode */, KeyEvent.FLAG_FROM_SYSTEM | KeyEvent.FLAG_VIRTUAL_HARD_KEY,
                InputDevice.SOURCE_KEYBOARD);

        ev.setDisplayId(displayId);
        if (!mContext.getSystemService(InputManager.class)
                .injectInputEvent(ev, InputManager.INJECT_INPUT_EVENT_MODE_ASYNC)) {
            Log.e(TAG, "Inject input event fail");
        }
    }

    void closeTask(WindowContainerToken taskToken) {
        closeTask(taskToken, new WindowContainerTransaction());
    }

    void closeTask(WindowContainerToken taskToken, WindowContainerTransaction wct) {
        wct.removeTask(taskToken);
        if (Transitions.ENABLE_SHELL_TRANSITIONS) {
            mTransitionStarter.startRemoveTransition(wct);
        } else {
            mSyncQueue.queue(wct);
        }
    }

    void minimizeTask(WindowContainerToken taskToken) {
        WindowContainerTransaction wct = new WindowContainerTransaction();
        wct.reorder(taskToken, false);
        if (Transitions.ENABLE_SHELL_TRANSITIONS) {
            mTransitionStarter.startMinimizedModeTransition(wct);
        } else {
            mSyncQueue.queue(wct);
        }
    }

    void maximizeTask(RunningTaskInfo taskInfo, int containerWindowingMode) {
        WindowContainerTransaction wct = new WindowContainerTransaction();
        int targetWindowingMode = taskInfo.getWindowingMode() != WINDOWING_MODE_FULLSCREEN
                ? WINDOWING_MODE_FULLSCREEN : WINDOWING_MODE_FREEFORM;
        wct.setWindowingMode(taskInfo.token,
                targetWindowingMode == containerWindowingMode
                        ? WINDOWING_MODE_UNDEFINED : targetWindowingMode);
        if (targetWindowingMode == WINDOWING_MODE_FULLSCREEN) {
            wct.setBounds(taskInfo.token, null);
        }
        if (Transitions.ENABLE_SHELL_TRANSITIONS) {
            mTransitionStarter.startWindowingModeTransition(targetWindowingMode, wct);
        } else {
            mSyncQueue.queue(wct);
        }
    }
}
