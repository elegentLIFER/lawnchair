

package com.android.wm.shell.desktopmode;

import android.graphics.Region;

import com.android.wm.shell.common.desktopmode.DesktopModeTransitionSource;
import com.android.wm.shell.shared.annotations.ExternalThread;

import java.util.concurrent.Executor;
import java.util.function.Consumer;

/**
 * Interface to interact with desktop mode feature in shell.
 */
@ExternalThread
public interface DesktopMode {

    /**
     * Adds a listener to find out about changes in the visibility of freeform tasks.
     *
     * @param listener the listener to add.
     * @param callbackExecutor the executor to call the listener on.
     */
    void addVisibleTasksListener(DesktopModeTaskRepository.VisibleTasksListener listener,
            Executor callbackExecutor);

    /**
     * Adds a consumer to listen for Desktop task corner changes. This is used for gesture
     * exclusion. The SparseArray contains a list of four corner resize handles mapped to each
     * desktop task's taskId. The resize handle Rects are stored in the following order:
     * left-top, left-bottom, right-top, right-bottom.
     */
    default void addDesktopGestureExclusionRegionListener(Consumer<Region> listener,
            Executor callbackExecutor) { }


    /** Called when requested to go to desktop mode from the current focused app. */
    void moveFocusedTaskToDesktop(int displayId, DesktopModeTransitionSource transitionSource);

    /** Called when requested to go to fullscreen from the current focused desktop app. */
    void moveFocusedTaskToFullscreen(int displayId, DesktopModeTransitionSource transitionSource);

    /** Called when requested to go to split screen from the current focused desktop app. */
    void moveFocusedTaskToStageSplit(int displayId, boolean leftOrTop);
}
