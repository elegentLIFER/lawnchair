
package com.android.launcher3.tapl;

import static com.android.launcher3.testing.shared.TestProtocol.TEST_DRAG_APP_ICON_TO_MULTIPLE_WORKSPACES_FAILURE;

import android.graphics.Point;
import android.util.Log;

import java.util.function.Supplier;

/** {@link Launchable} that can serve as a source for dragging and dropping to the workspace. */
interface WorkspaceDragSource {

    /**
     * Drags an object to the center of homescreen.
     *
     * @param startsActivity   whether it's expected to start an activity.
     * @param isWidgetShortcut whether we drag a widget shortcut
     */
    default void dragToWorkspace(boolean startsActivity, boolean isWidgetShortcut) {
        Launchable launchable = getLaunchable();
        LauncherInstrumentation launcher = launchable.mLauncher;
        try (LauncherInstrumentation.Closable e = launcher.eventsCheck()) {
            internalDragToWorkspace(startsActivity, isWidgetShortcut);
        }
    }

    /**
     * TODO(Redesign WorkspaceDragSource to have actual private methods)
     * Temporary private method
     *
     * @param startsActivity   whether it's expected to start an activity.
     * @param isWidgetShortcut whether we drag a widget shortcut
     */
    default void internalDragToWorkspace(boolean startsActivity, boolean isWidgetShortcut) {
        Launchable launchable = getLaunchable();
        LauncherInstrumentation launcher = launchable.mLauncher;
        final Point launchableCenter = launchable.getObject().getVisibleCenter();
        final Point displaySize = launcher.getRealDisplaySize();
        final int width = displaySize.x / 2;
        Workspace.dragIconToWorkspace(
                launcher,
                launchable,
                () -> new Point(
                        launchableCenter.x >= width
                                ? launchableCenter.x - width / 2
                                : launchableCenter.x + width / 2,
                        displaySize.y / 2),
                startsActivity,
                isWidgetShortcut,
                launchable::addExpectedEventsForLongClick);
    }

    /**
     * Drag an object to the given cell in workspace. The target cell must be empty.
     *
     * @param cellX zero based column number, starting from the left of the screen.
     * @param cellY zero based row number, starting from the top of the screen.     *
     */
    default HomeAppIcon dragToWorkspace(int cellX, int cellY) {
        Launchable launchable = getLaunchable();
        final String iconName = launchable.getObject().getText();
        LauncherInstrumentation launcher = launchable.mLauncher;
        try (LauncherInstrumentation.Closable e = launcher.eventsCheck();
             LauncherInstrumentation.Closable c = launcher.addContextLayer(
                     String.format("want to drag the icon to cell(%d, %d)", cellX, cellY))) {
            final Supplier<Point> dest = () -> Workspace.getCellCenter(launcher, cellX, cellY);
            Log.d(TEST_DRAG_APP_ICON_TO_MULTIPLE_WORKSPACES_FAILURE,
                    "WorkspaceDragSource.dragToWorkspace: dragging icon to workspace | dest: "
                            + dest.get());
            Workspace.dragIconToWorkspace(
                    launcher,
                    launchable,
                    dest,
                    launchable::addExpectedEventsForLongClick,
                    /*expectDropEvents= */ null,
                    /* startsActivity = */ false);

            try (LauncherInstrumentation.Closable ignore = launcher.addContextLayer("dragged")) {
                WorkspaceAppIcon appIcon =
                        (WorkspaceAppIcon) launcher.getWorkspace().getWorkspaceAppIcon(iconName);
                launcher.assertTrue(
                        String.format(
                                "The %s icon should be in the cell (%d, %d).", iconName, cellX,
                                cellY),
                        appIcon.isInCell(cellX, cellY));
                return appIcon;
            }
        }
    }

    /** This method requires public access, however should not be called in tests. */
    Launchable getLaunchable();
}
