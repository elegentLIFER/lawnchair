

package com.android.wm.shell.transition;

import android.annotation.NonNull;
import android.annotation.Nullable;
import android.os.IBinder;
import android.util.Slog;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;

/**
 * A Simple handler that tracks SLEEP transitions. We track them specially since we (ab)use these
 * as sentinels for fast-forwarding through animations when the screen is off.
 *
 * There should only be one SleepHandler and it is used explicitly by {@link Transitions} so we
 * don't register it like a normal handler.
 */
class SleepHandler implements Transitions.TransitionHandler {
    @Override
    public boolean startAnimation(@NonNull IBinder transition, @NonNull TransitionInfo info,
            @NonNull SurfaceControl.Transaction startTransaction,
            @NonNull SurfaceControl.Transaction finishTransaction,
            @NonNull Transitions.TransitionFinishCallback finishCallback) {
        if (info.hasChangesOrSideEffects()) {
            Slog.e(Transitions.TAG, "Real changes included in a SLEEP transition");
            return false;
        } else {
            startTransaction.apply();
            finishCallback.onTransitionFinished(null);
            return true;
        }
    }

    @Override
    @Nullable
    public WindowContainerTransaction handleRequest(@NonNull IBinder transition,
            @NonNull TransitionRequestInfo request) {
        return new WindowContainerTransaction();
    }
}
