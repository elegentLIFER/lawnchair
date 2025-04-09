

package com.android.systemui.flags

import android.content.ContentResolver
import android.database.ContentObserver
import android.provider.Settings

class FlagSettingsHelper(private val contentResolver: ContentResolver) {

    fun getStringFromSecure(key: String): String? = Settings.Secure.getString(contentResolver, key)

    fun getString(key: String): String? = Settings.Global.getString(contentResolver, key)

    fun registerContentObserver(
        name: String,
        notifyForDescendants: Boolean,
        observer: ContentObserver
    ) {
        contentResolver.registerContentObserver(
            Settings.Secure.getUriFor(name),
            notifyForDescendants,
            observer
        )
    }

    fun unregisterContentObserver(observer: ContentObserver) {
        contentResolver.unregisterContentObserver(observer)
    }
}
