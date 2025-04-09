
package com.android.launcher3.model;

import android.os.UserHandle;

import androidx.annotation.NonNull;

import com.android.launcher3.LauncherModel.ModelUpdateTask;
import com.android.launcher3.model.data.AppInfo;
import com.android.launcher3.model.data.ItemInfoWithIcon;
import com.android.launcher3.model.data.WorkspaceItemInfo;
import com.android.launcher3.pm.PackageInstallInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles updates due to incremental download progress updates.
 */
public class PackageIncrementalDownloadUpdatedTask implements ModelUpdateTask {

    @NonNull
    private final UserHandle mUser;

    private final int mProgress;

    @NonNull
    private final String mPackageName;

    public PackageIncrementalDownloadUpdatedTask(@NonNull final String packageName,
            @NonNull final UserHandle user, final float progress) {
        mUser = user;
        mProgress = 1 - progress > 0.001 ? (int) (100 * progress) : 100;
        mPackageName = packageName;
    }

    @Override
    public void execute(@NonNull ModelTaskController taskController, @NonNull BgDataModel dataModel,
            @NonNull AllAppsList appsList) {
        PackageInstallInfo downloadInfo = new PackageInstallInfo(
                mPackageName,
                PackageInstallInfo.STATUS_INSTALLED_DOWNLOADING,
                mProgress,
                mUser);

        synchronized (appsList) {
            List<AppInfo> updatedAppInfos = appsList.updatePromiseInstallInfo(downloadInfo);
            if (!updatedAppInfos.isEmpty()) {
                for (AppInfo appInfo : updatedAppInfos) {
                    appInfo.runtimeStatusFlags &= ~ItemInfoWithIcon.FLAG_INSTALL_SESSION_ACTIVE;
                    taskController.scheduleCallbackTask(
                            c -> c.bindIncrementalDownloadProgressUpdated(appInfo));
                }
            }
            taskController.bindApplicationsIfNeeded();
        }

        final ArrayList<WorkspaceItemInfo> updatedWorkspaceItems = new ArrayList<>();
        synchronized (dataModel) {
            dataModel.forAllWorkspaceItemInfos(mUser, si -> {
                if (mPackageName.equals(si.getTargetPackage())) {
                    si.runtimeStatusFlags &= ~ItemInfoWithIcon.FLAG_INSTALL_SESSION_ACTIVE;
                    si.setProgressLevel(downloadInfo);
                    updatedWorkspaceItems.add(si);
                }
            });
        }
        taskController.bindUpdatedWorkspaceItems(updatedWorkspaceItems);
    }
}
