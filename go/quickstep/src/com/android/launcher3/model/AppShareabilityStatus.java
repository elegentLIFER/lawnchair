
package com.android.launcher3.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.android.launcher3.model.AppShareabilityManager.ShareabilityStatus;

/**
 * Database entry to hold the shareability status of a single app
 */
@Entity
public class AppShareabilityStatus {
    @PrimaryKey
    @NonNull
    public String packageName;

    public @ShareabilityStatus int status;

    public AppShareabilityStatus(@NonNull String packageName, @ShareabilityStatus int status) {
        this.packageName = packageName;
        this.status = status;
    }
}
