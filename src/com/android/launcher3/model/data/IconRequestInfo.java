
package com.android.launcher3.model.data;

import static android.graphics.BitmapFactory.decodeByteArray;

import android.content.Context;
import android.content.pm.LauncherActivityInfo;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.launcher3.icons.LauncherIcons;

/**
 * Class representing one request for an icon to be queried in a sql database.
 *
 * @param <T> ItemInfoWithIcon subclass whose title and icon can be loaded and filled by an sql
 *           query.
 */
public class IconRequestInfo<T extends ItemInfoWithIcon> {

    private static final String TAG = "IconRequestInfo";

    @NonNull public final T itemInfo;
    @Nullable public final LauncherActivityInfo launcherActivityInfo;
    @Nullable public final byte[] iconBlob;
    public final boolean useLowResIcon;

    public IconRequestInfo(
            @NonNull T itemInfo,
            @Nullable LauncherActivityInfo launcherActivityInfo,
            boolean useLowResIcon) {
        this(
                itemInfo,
                launcherActivityInfo,
                /* iconBlob= */ null,
                useLowResIcon);
    }

    public IconRequestInfo(
            @NonNull T itemInfo,
            @Nullable LauncherActivityInfo launcherActivityInfo,
            @Nullable byte[] iconBlob,
            boolean useLowResIcon) {
        this.itemInfo = itemInfo;
        this.launcherActivityInfo = launcherActivityInfo;
        this.iconBlob = iconBlob;
        this.useLowResIcon = useLowResIcon;
    }

    /**
     * Loads this request's item info's title. This method should only be used on IconRequestInfos
     * for WorkspaceItemInfos.
     */
    public boolean loadWorkspaceIcon(Context context) {
        if (!(itemInfo instanceof WorkspaceItemInfo)) {
            throw new IllegalStateException(
                    "loadWorkspaceIcon should only be use for a WorkspaceItemInfos: " + itemInfo);
        }

        try (LauncherIcons li = LauncherIcons.obtain(context)) {
            WorkspaceItemInfo info = (WorkspaceItemInfo) itemInfo;
            // Failed to load from resource, try loading from DB.
            if (iconBlob == null) {
                return false;
            }
            info.bitmap = li.createIconBitmap(decodeByteArray(
                    iconBlob, 0, iconBlob.length));
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Failed to decode byte array for info " + itemInfo, e);
            return false;
        }
    }
}
