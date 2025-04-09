
package com.android.launcher3.model;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RoomDatabase;
import androidx.room.Update;

import java.util.List;

/**
 * This database maintains a collection of AppShareabilityStatus items
 * In its intended use case, there will be one entry for each app installed on the device
 */
@Database(entities = {AppShareabilityStatus.class}, exportSchema = false, version = 1)
public abstract class AppShareabilityDatabase extends RoomDatabase {
    /**
     * Data Access Object for this database
     */
    @Dao
    public interface ShareabilityDao {
        /** Add an AppShareabilityStatus to the database */
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insertAppStatus(AppShareabilityStatus status);

        /** Add a collection of AppShareabilityStatus objects to the database */
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insertAppStatuses(AppShareabilityStatus... statuses);

        /**
         * Update an AppShareabilityStatus in the database
         * @return The number of entries successfully updated
         */
        @Update
        int updateAppStatus(AppShareabilityStatus status);

        /** Retrieve all entries from the database */
        @Query("SELECT * FROM AppShareabilityStatus")
        List<AppShareabilityStatus> getAllEntries();
    }

    protected abstract ShareabilityDao shareabilityDao();
}
