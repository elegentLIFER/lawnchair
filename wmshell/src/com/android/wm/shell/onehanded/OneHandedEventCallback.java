

package com.android.wm.shell.onehanded;

/**
 * Additional callback interface for OneHanded events.
 */
public interface OneHandedEventCallback {
    /**
     * Called to notify expand notification shade.
     */
    default void notifyExpandNotification() {
    }
}
