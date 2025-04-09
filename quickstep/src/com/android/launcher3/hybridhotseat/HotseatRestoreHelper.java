
package com.android.launcher3.hybridhotseat;

import static com.android.launcher3.LauncherSettings.Favorites.HYBRID_HOTSEAT_BACKUP_TABLE;
import static com.android.launcher3.provider.LauncherDbUtils.tableExists;
import static com.android.launcher3.util.Executors.MODEL_EXECUTOR;

import android.content.Context;

import com.android.launcher3.LauncherAppState;
import com.android.launcher3.LauncherModel;
import com.android.launcher3.model.GridBackupTable;
import com.android.launcher3.model.ModelDbController;
import com.android.launcher3.provider.LauncherDbUtils.SQLiteTransaction;

/**
 * A helper class to manage migration revert restoration for hybrid hotseat
 */
public class HotseatRestoreHelper {

    /**
     * Creates a snapshot backup of Favorite table for future restoration use.
     */
    public static void createBackup(Context context) {
        MODEL_EXECUTOR.execute(() -> {
            ModelDbController dbController = LauncherAppState.getInstance(context)
                    .getModel().getModelDbController();
            try (SQLiteTransaction transaction = dbController.newTransaction()) {
                GridBackupTable backupTable = new GridBackupTable(context, transaction.getDb());
                backupTable.createCustomBackupTable(HYBRID_HOTSEAT_BACKUP_TABLE);
                transaction.commit();
                dbController.refreshHotseatRestoreTable();
            }
        });
    }

    /**
     * Finds and restores a previously saved snapshow of Favorites table
     */
    public static void restoreBackup(Context context) {
        MODEL_EXECUTOR.execute(() -> {
            LauncherModel model = LauncherAppState.getInstance(context).getModel();
            try (SQLiteTransaction transaction = model.getModelDbController().newTransaction()) {
                if (!tableExists(transaction.getDb(), HYBRID_HOTSEAT_BACKUP_TABLE)) {
                    return;
                }
                GridBackupTable backupTable = new GridBackupTable(context, transaction.getDb());
                backupTable.restoreFromCustomBackupTable(HYBRID_HOTSEAT_BACKUP_TABLE, true);
                transaction.commit();
                model.forceReload();
            }
        });
    }
}
