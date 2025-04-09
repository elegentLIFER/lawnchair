
package com.android.wm.shell.bubbles;

import android.os.Binder;
import android.os.IBinder;

// Copied from Launcher3
/**
 * Utility class to pass non-parcealable objects within same process using parcealable payload.
 *
 * It wraps the object in a binder as binders are singleton within a process
 */
public class ObjectWrapper<T> extends Binder {

    private T mObject;

    public ObjectWrapper(T object) {
        mObject = object;
    }

    public T get() {
        return mObject;
    }

    public void clear() {
        mObject = null;
    }

    public static IBinder wrap(Object obj) {
        return new ObjectWrapper<>(obj);
    }
}
