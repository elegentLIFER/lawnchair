
package com.android.wm.shell.common.bubbles

import android.os.Parcel
import android.os.Parcelable

/**
 * The location of the bubble bar.
 */
enum class BubbleBarLocation : Parcelable {
    /**
     * Place bubble bar at the default location for the chosen system language.
     * If an RTL language is used, it is on the left. Otherwise on the right.
     */
    DEFAULT,
    /** Default bubble bar location is overridden. Place bubble bar on the left. */
    LEFT,
    /** Default bubble bar location is overridden. Place bubble bar on the right. */
    RIGHT;

    /**
     * Returns whether bubble bar is pinned to the left edge or right edge.
     */
    fun isOnLeft(isRtl: Boolean): Boolean {
        if (this == DEFAULT) {
            return isRtl
        }
        return this == LEFT
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<BubbleBarLocation> {
            override fun createFromParcel(parcel: Parcel): BubbleBarLocation {
                return parcel.readString()?.let { valueOf(it) } ?: DEFAULT
            }

            override fun newArray(size: Int) = arrayOfNulls<BubbleBarLocation>(size)
        }
    }
}
