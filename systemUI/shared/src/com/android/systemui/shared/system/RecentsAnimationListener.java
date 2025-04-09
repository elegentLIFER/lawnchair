
package com.android.systemui.shared.system;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.window.TransitionInfo;

import com.android.systemui.shared.recents.model.ThumbnailData;

import java.util.HashMap;

public interface RecentsAnimationListener {
    /**
     * Called when the animation into Recents can start. This call is made on the binder thread.
     */
    void onAnimationStart(RecentsAnimationControllerCompat controller,
            RemoteAnimationTarget[] apps, RemoteAnimationTarget[] wallpapers,
            Rect homeContentInsets, Rect minimizedHomeBounds, Bundle extras);

    // Introduced in NothingOS 2.5.5, needed in 2.6
    void onAnimationStart(RecentsAnimationControllerCompat controller,
            TransitionInfo transitionInfo, SurfaceControl.Transaction transaction,
            RemoteAnimationTarget[] apps, RemoteAnimationTarget[] wallpapers,
            Rect homeContentInsets, Rect minimizedHomeBounds);

    /**
     * Called when the animation into Recents was canceled. This call is made on the binder thread.
     */
    void onAnimationCanceled(HashMap<Integer, ThumbnailData> thumbnailDatas);

    /**
     * Called when the task of an activity that has been started while the recents animation
     * was running becomes ready for control.
     */
    void onTasksAppeared(RemoteAnimationTarget[] app);

    /**
     * Called to request that the current task tile be switched out for a screenshot (if not
     * already). Once complete, onFinished should be called.
     * @return true if this impl will call onFinished. No other onSwitchToScreenshot impls will
     *         be called afterwards (to avoid multiple calls to onFinished).
     */
    default boolean onSwitchToScreenshot(Runnable onFinished) {
        return false;
    }
}
