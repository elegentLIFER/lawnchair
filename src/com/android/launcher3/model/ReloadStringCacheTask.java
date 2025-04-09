
package com.android.launcher3.model;

import androidx.annotation.NonNull;

import com.android.launcher3.LauncherModel.ModelUpdateTask;

/**
 * Handles updates due to changes in Device Policy Management resources triggered by
 * {@link android.app.admin.DevicePolicyManager#ACTION_DEVICE_POLICY_RESOURCE_UPDATED}.
 */
public class ReloadStringCacheTask implements ModelUpdateTask {

    @NonNull
    private ModelDelegate mModelDelegate;

    public ReloadStringCacheTask(@NonNull final ModelDelegate modelDelegate) {
        mModelDelegate = modelDelegate;
    }

    @Override
    public void execute(@NonNull ModelTaskController taskController, @NonNull BgDataModel dataModel,
            @NonNull AllAppsList apps) {
        synchronized (dataModel) {
            mModelDelegate.loadStringCache(dataModel.stringCache);
            StringCache cloneSC = dataModel.stringCache.clone();
            taskController.scheduleCallbackTask(c -> c.bindStringCache(cloneSC));
        }
    }
}
