

package com.android.launcher3.notification;

import android.app.Notification;
import android.app.Person;
import android.service.notification.StatusBarNotification;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;

import com.android.launcher3.Utilities;

import java.util.ArrayList;

/**
 * The key data associated with the notification, used to determine what to include
 * in dots and stub popup views before they are populated.
 */
public class NotificationKeyData {
    public final String notificationKey;
    public final String shortcutId;
    @NonNull
    public final String[] personKeysFromNotification;
    public int count;

    @VisibleForTesting
    public NotificationKeyData(String notificationKey) {
        this(notificationKey, null, 1, new String[]{});
    }

    private NotificationKeyData(String notificationKey, String shortcutId, int count,
            String[] personKeysFromNotification) {
        this.notificationKey = notificationKey;
        this.shortcutId = shortcutId;
        this.count = Math.max(1, count);
        this.personKeysFromNotification = personKeysFromNotification;
    }

    public static NotificationKeyData fromNotification(StatusBarNotification sbn) {
        Notification notif = sbn.getNotification();
        return new NotificationKeyData(sbn.getKey(), notif.getShortcutId(), notif.number,
                extractPersonKeyOnly(notif.extras.getParcelableArrayList(
                        Notification.EXTRA_PEOPLE_LIST)));
    }

    private static String[] extractPersonKeyOnly(@Nullable ArrayList<Person> people) {
        if (people == null || people.isEmpty()) {
            return Utilities.EMPTY_STRING_ARRAY;
        }
        return people.stream().filter(person -> person.getKey() != null)
                .map(Person::getKey).sorted().toArray(String[]::new);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof NotificationKeyData)) {
            return false;
        }
        // Only compare the keys.
        return ((NotificationKeyData) obj).notificationKey.equals(notificationKey);
    }
}
