
package com.android.launcher3.model;

import com.android.launcher3.LauncherSettings;
import com.android.launcher3.model.data.ItemInfo;
import com.android.launcher3.util.IntArray;
import com.android.launcher3.util.IntSet;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * Utils class for {@link com.android.launcher3.LauncherModel}.
 */
public class ModelUtils {

    /**
     * Filters the set of items who are directly or indirectly (via another container) on the
     * specified screen.
     */
    public static <T extends ItemInfo> void filterCurrentWorkspaceItems(
            final IntSet currentScreenIds,
            List<? extends T> allWorkspaceItems,
            List<T> currentScreenItems,
            List<T> otherScreenItems) {
        // Purge any null ItemInfos
        allWorkspaceItems.removeIf(Objects::isNull);
        // Order the set of items by their containers first, this allows use to walk through the
        // list sequentially, build up a list of containers that are in the specified screen,
        // as well as all items in those containers.
        IntSet itemsOnScreen = new IntSet();
        Collections.sort(allWorkspaceItems,
                (lhs, rhs) -> Integer.compare(lhs.container, rhs.container));
        for (T info : allWorkspaceItems) {
            if (info.container == LauncherSettings.Favorites.CONTAINER_DESKTOP) {
                if (currentScreenIds.contains(info.screenId)) {
                    currentScreenItems.add(info);
                    itemsOnScreen.add(info.id);
                } else {
                    otherScreenItems.add(info);
                }
            } else if (info.container == LauncherSettings.Favorites.CONTAINER_HOTSEAT) {
                currentScreenItems.add(info);
                itemsOnScreen.add(info.id);
            } else {
                if (itemsOnScreen.contains(info.container)) {
                    currentScreenItems.add(info);
                    itemsOnScreen.add(info.id);
                } else {
                    otherScreenItems.add(info);
                }
            }
        }
    }

    /**
     * Iterates though current workspace items and returns available hotseat ranks for prediction.
     */
    public static IntArray getMissingHotseatRanks(List<ItemInfo> items, int len) {
        IntSet seen = new IntSet();
        items.stream().filter(
                info -> info.container == LauncherSettings.Favorites.CONTAINER_HOTSEAT)
                .forEach(i -> seen.add(i.screenId));
        IntArray result = new IntArray(len);
        IntStream.range(0, len).filter(i -> !seen.contains(i)).forEach(result::add);
        return result;
    }
}
