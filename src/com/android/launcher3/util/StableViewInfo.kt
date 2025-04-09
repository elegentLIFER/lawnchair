

package com.android.launcher3.util

import android.os.IBinder
import com.android.launcher3.model.data.ItemInfo
import com.android.launcher3.model.data.ItemInfo.NO_ID

/** Info parameters that can be used to identify a Launcher object */
data class StableViewInfo(val itemId: Int, val containerId: Int, val stableId: Any) {

    fun matches(info: ItemInfo?) =
        info != null &&
                itemId == info.id &&
                containerId == info.container &&
                stableId == info.stableId

    companion object {

        private fun ItemInfo.toStableViewInfo() =
            stableId?.let { sId ->
                if (id != NO_ID || container != NO_ID) StableViewInfo(id, container, sId) else null
            }

        /**
         * Return a new launch cookie for the activity launch if supported.
         *
         * @param info the item info for the launch
         */
        @JvmStatic
        fun toLaunchCookie(info: ItemInfo?) =
            info?.toStableViewInfo()?.let { ObjectWrapper.wrap(it) }

        /**
         * Unwraps the binder and returns the first non-null StableViewInfo in the list or null if
         * none can be found
         */
        @JvmStatic
        fun fromLaunchCookies(launchCookies: List<IBinder>) =
            launchCookies.firstNotNullOfOrNull { ObjectWrapper.unwrap<StableViewInfo>(it) }
    }
}
