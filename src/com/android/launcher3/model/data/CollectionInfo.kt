

package com.android.launcher3.model.data

import com.android.launcher3.LauncherSettings
import com.android.launcher3.logger.LauncherAtom
import com.android.launcher3.util.ContentWriter
import java.util.function.Predicate

abstract class CollectionInfo : ItemInfo() {
    /** Adds an ItemInfo to the collection. Throws if given an illegal type. */
    abstract fun add(item: ItemInfo)

    /** Returns the collection's contents as an ArrayList of [ItemInfo]. */
    abstract fun getContents(): ArrayList<ItemInfo>

    /**
     * Returns the collection's contents as an ArrayList of [WorkspaceItemInfo]. Does not include
     * other collection [ItemInfo]s that are inside this collection; rather, it should collect
     * *their* contents and adds them to the ArrayList.
     */
    abstract fun getAppContents(): ArrayList<WorkspaceItemInfo>

    /** Convenience function. Checks contents to see if any match a given predicate. */
    fun anyMatch(matcher: Predicate<ItemInfo>) = getContents().stream().anyMatch(matcher)

    override fun onAddToDatabase(writer: ContentWriter) {
        super.onAddToDatabase(writer)
        writer.put(LauncherSettings.Favorites.TITLE, title)
    }

    /** Returns the collection wrapped as {@link LauncherAtom.ItemInfo} for logging. */
    override fun buildProto(): LauncherAtom.ItemInfo {
        return buildProto(null)
    }
}
