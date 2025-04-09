

package com.android.systemui.plugins;

import android.annotation.Nullable;
import android.graphics.drawable.Drawable;
import android.service.notification.StatusBarNotification;

import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;

/** Custom logic that can extract a PeopleHub "person" from a notification. */
@ProvidesInterface(
        action = NotificationPersonExtractorPlugin.ACTION,
        version = NotificationPersonExtractorPlugin.VERSION)
@DependsOn(target = NotificationPersonExtractorPlugin.PersonData.class)
public interface NotificationPersonExtractorPlugin extends Plugin {

    String ACTION = "com.android.systemui.action.PEOPLE_HUB_PERSON_EXTRACTOR";
    int VERSION = 1;

    /**
     * Attempts to extract a person from a notification. Returns {@code null} if one is not found.
     */
    @Nullable
    PersonData extractPerson(StatusBarNotification sbn);

    /**
     * Attempts to extract a person id from a notification. Returns {@code null} if one is not
     * found.
     *
     * This method can be overridden in order to provide a faster implementation.
     */
    @Nullable
    default String extractPersonKey(StatusBarNotification sbn) {
        return extractPerson(sbn).key;
    }

    /**
     * Determines whether or not a notification should be treated as having a person. Used for
     * appropriate positioning in the notification shade.
     */
    default boolean isPersonNotification(StatusBarNotification sbn) {
        return extractPersonKey(sbn) != null;
    }

    /** A person to be surfaced in PeopleHub. */
    @ProvidesInterface(version = PersonData.VERSION)
    final class PersonData {

        public static final int VERSION = 0;

        public final String key;
        public final CharSequence name;
        public final Drawable avatar;
        public final Runnable clickRunnable;

        public PersonData(String key, CharSequence name, Drawable avatar,
                Runnable clickRunnable) {
            this.key = key;
            this.name = name;
            this.avatar = avatar;
            this.clickRunnable = clickRunnable;
        }
    }
}
