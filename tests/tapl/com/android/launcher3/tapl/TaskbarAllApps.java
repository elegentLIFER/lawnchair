
package com.android.launcher3.tapl;

import androidx.annotation.NonNull;
import androidx.test.uiautomator.UiObject2;

import com.android.launcher3.testing.shared.TestProtocol;

/**
 * Operations on AllApps opened from the Taskbar.
 */
public class TaskbarAllApps extends AllApps {

    TaskbarAllApps(LauncherInstrumentation launcher) {
        super(launcher);
    }

    @Override
    protected LauncherInstrumentation.ContainerType getContainerType() {
        return LauncherInstrumentation.ContainerType.TASKBAR_ALL_APPS;
    }

    @NonNull
    @Override
    public TaskbarAppIcon getAppIcon(String appName) {
        return (TaskbarAppIcon) super.getAppIcon(appName);
    }

    @NonNull
    @Override
    protected TaskbarAppIcon createAppIcon(UiObject2 icon) {
        return new TaskbarAppIcon(mLauncher, icon);
    }

    @Override
    protected boolean hasSearchBox() {
        return false;
    }

    @Override
    protected int getAppsListRecyclerTopPadding() {
        return mLauncher.getTestInfo(TestProtocol.REQUEST_TASKBAR_ALL_APPS_TOP_PADDING)
                .getInt(TestProtocol.TEST_INFO_RESPONSE_FIELD);
    }

    @Override
    protected int getAllAppsScroll() {
        return mLauncher.getTestInfo(TestProtocol.REQUEST_TASKBAR_APPS_LIST_SCROLL_Y)
                .getInt(TestProtocol.TEST_INFO_RESPONSE_FIELD);
    }

    @NonNull
    @Override
    public TaskbarAllAppsQsb getQsb() {
        return new TaskbarAllAppsQsb(mLauncher, verifyActiveContainer());
    }

    @Override
    protected void verifyVisibleContainerOnDismiss() {
        mLauncher.getLaunchedAppState().assertTaskbarVisible();
    }

    @Override
    public boolean isHomeState() {
        return false;
    }
}
