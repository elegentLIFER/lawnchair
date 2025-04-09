

package com.android.wm.shell.onehanded;

/**
 * Interface that is exposed to remote callers to manipulate the OneHanded feature.
 */
interface IOneHanded {

    /**
     * Enters one handed mode.
     */
    oneway void startOneHanded() = 1;

    /**
     * Exits one handed mode.
     */
    oneway void stopOneHanded() = 2;
}
