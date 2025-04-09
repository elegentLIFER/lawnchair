

package com.android.launcher3.util;

import android.content.ComponentName;
import android.os.UserHandle;

import androidx.annotation.NonNull;

import com.android.launcher3.LauncherSettings.Favorites;
import com.android.launcher3.model.data.FolderInfo;
import com.android.launcher3.model.data.ItemInfo;
import com.android.launcher3.shortcuts.ShortcutKey;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * A utility class to check for {@link ItemInfo}
 */
public abstract class ItemInfoMatcher {

    /**
     * Empty component used for match testing
     */
    private static final ComponentName EMPTY_COMPONENT = new ComponentName("", "");

    public static Predicate<ItemInfo> ofUser(UserHandle user) {
        return info -> info != null && info.user.equals(user);
    }

    public static Predicate<ItemInfo> ofComponents(
            HashSet<ComponentName> components, UserHandle user) {
        return info -> info != null && info.user.equals(user)
                && components.contains(getNonNullComponent(info));
    }

    public static Predicate<ItemInfo> ofPackages(Set<String> packageNames, UserHandle user) {
        return info -> info != null && info.user.equals(user)
                && packageNames.contains(getNonNullComponent(info).getPackageName());
    }

    public static Predicate<ItemInfo> ofShortcutKeys(Set<ShortcutKey> keys) {
        return info -> info != null && info.itemType == Favorites.ITEM_TYPE_DEEP_SHORTCUT
                && keys.contains(ShortcutKey.fromItemInfo(info));
    }

    /**
     * Returns a matcher for items within folders.
     */
    public static Predicate<ItemInfo> forFolderMatch(Predicate<ItemInfo> childOperator) {
        return info -> info instanceof FolderInfo && ((FolderInfo) info).getContents().stream()
                .anyMatch(childOperator);
    }

    /**
     * Returns a matcher for items with provided ids
     */
    public static Predicate<ItemInfo> ofItemIds(IntSet ids) {
        return info -> info != null && ids.contains(info.id);
    }

    /**
     * Returns a matcher for items with provided items
     */
    public static Predicate<ItemInfo> ofItems(Collection<? extends ItemInfo> items) {
        IntSet ids = new IntSet();
        items.forEach(item -> ids.add(item.id));
        return ofItemIds(ids);
    }

    private static ComponentName getNonNullComponent(@NonNull ItemInfo info) {
        ComponentName cn = info.getTargetComponent();
        return cn != null ? cn : EMPTY_COMPONENT;
    }
}
