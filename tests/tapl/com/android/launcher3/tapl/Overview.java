

package com.android.launcher3.tapl;

import com.android.launcher3.tapl.LauncherInstrumentation.ContainerType;

/**
 * Overview pane.
 */
public class Overview extends BaseOverview {

    Overview(LauncherInstrumentation launcher) {
        super(launcher);
    }

    @Override
    protected ContainerType getContainerType() {
        return ContainerType.OVERVIEW;
    }

    @Override
    public void dismissAllTasks() {
        super.dismissAllTasks();
        try (LauncherInstrumentation.Closable c1 = mLauncher.addContextLayer(
                "dismissed all tasks")) {
            new Workspace(mLauncher);
        }
    }
}
