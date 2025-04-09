

package com.android.wm.shell.common.bubbles;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Represents a removed bubble, defining the key and reason the bubble was removed.
 */
public class RemovedBubble implements Parcelable {

    private final String mKey;
    private final int mRemovalReason;

    public RemovedBubble(String key, int removalReason) {
        mKey = key;
        mRemovalReason = removalReason;
    }

    public RemovedBubble(Parcel parcel) {
        mKey = parcel.readString();
        mRemovalReason = parcel.readInt();
    }

    public String getKey() {
        return mKey;
    }

    public int getRemovalReason() {
        return mRemovalReason;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mKey);
        dest.writeInt(mRemovalReason);
    }

    @NonNull
    public static final Creator<RemovedBubble> CREATOR =
            new Creator<RemovedBubble>() {
                public RemovedBubble createFromParcel(Parcel source) {
                    return new RemovedBubble(source);
                }
                public RemovedBubble[] newArray(int size) {
                    return new RemovedBubble[size];
                }
            };
}
