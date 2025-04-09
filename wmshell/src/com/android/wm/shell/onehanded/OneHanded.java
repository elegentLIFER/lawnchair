

package com.android.wm.shell.onehanded;

import android.os.SystemProperties;

import com.android.wm.shell.shared.annotations.ExternalThread;

/**
 * Interface to engage one handed feature.
 */
@ExternalThread
public interface OneHanded {

    boolean sIsSupportOneHandedMode =  SystemProperties.getBoolean(
            OneHandedController.SUPPORT_ONE_HANDED_MODE, false);

    /**
     * Enters one handed mode.
     */
    void startOneHanded();

    /**
     * Exits one handed mode.
     */
    void stopOneHanded();

    /**
     * Exits one handed mode with {@link OneHandedUiEventLogger}.
     */
    void stopOneHanded(int uiEvent);

    /**
     * Sets one handed feature temporary locked in enabled or disabled state, this won't change
     * settings configuration.
     *
     * @param locked locked function in disabled(can not trigger) or enabled state.
     * @param enabled function in disabled(can not trigger) or enabled state.
     */
    void setLockedDisabled(boolean locked, boolean enabled);

    /**
     * Registers callback to notify WMShell when user tap shortcut to expand notification.
     */
    void registerEventCallback(OneHandedEventCallback callback);

    /**
     * Registers callback to be notified after {@link OneHandedDisplayAreaOrganizer}
     * transition start or finish
     */
    void registerTransitionCallback(OneHandedTransitionCallback callback);
}
