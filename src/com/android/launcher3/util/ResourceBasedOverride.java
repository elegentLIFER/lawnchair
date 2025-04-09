
package com.android.launcher3.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;

/**
 * An interface to indicate that a class is dynamically loaded using resource overlay, hence its
 * class name and constructor should be preserved by proguard
 */
public interface ResourceBasedOverride {

    class Overrides {

        private static final String TAG = "Overrides";

        public static <T extends ResourceBasedOverride> T getObject(
                Class<T> clazz, Context context, int resId) {
            String className = context.getString(resId);
            boolean isOverridden = !TextUtils.isEmpty(className);

            // First try to load the class with "Context" param
            try {
                Class<?> cls = isOverridden ? Class.forName(className) : clazz;
                return (T) cls.getDeclaredConstructor(Context.class).newInstance(context);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                     | ClassCastException | NoSuchMethodException | InvocationTargetException e) {
                if (isOverridden) {
                    Log.e(TAG, "Bad overriden class", e);
                }
            }

            // Load the base class with no parameter
            try {
                return clazz.newInstance();
            } catch (InstantiationException|IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
