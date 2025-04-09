

package com.android.launcher3.taskbar;

/**
 * Various utilities shared amongst the Taskbar's classes.
 */
public final class Utilities {

    private Utilities() {}

    /**
     * Sets drag, long-click, and split selection behavior on 1P and 3P launchers with Taskbar
     */
    static void setOverviewDragState(TaskbarControllers controllers,
            boolean disallowGlobalDrag, boolean disallowLongClick,
            boolean allowInitialSplitSelection) {
        controllers.taskbarDragController.setDisallowGlobalDrag(disallowGlobalDrag);
        controllers.taskbarDragController.setDisallowLongClick(disallowLongClick);
        controllers.taskbarAllAppsController.setDisallowGlobalDrag(disallowGlobalDrag);
        controllers.taskbarAllAppsController.setDisallowLongClick(disallowLongClick);
        controllers.taskbarPopupController.setAllowInitialSplitSelection(
                allowInitialSplitSelection);
    }
}
