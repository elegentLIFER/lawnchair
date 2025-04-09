

package com.android.launcher3.tapl;

import android.graphics.Point;

import androidx.test.uiautomator.UiObject2;

import java.util.regex.Pattern;

/**
 * App icon in workspace.
 */
final class WorkspaceAppIcon extends HomeAppIcon {

    WorkspaceAppIcon(LauncherInstrumentation launcher, UiObject2 icon) {
        super(launcher, icon);
    }

    @Override
    protected Pattern getLongClickEvent() {
        return Workspace.LONG_CLICK_EVENT;
    }

    boolean isInCell(int cellX, int cellY) {
        final Point center = Workspace.getCellCenter(mLauncher, cellX, cellY);
        return mObject.getParent().getVisibleBounds().contains(center.x, center.y);
    }
}
