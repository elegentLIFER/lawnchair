

package com.android.launcher3.util;

import java.lang.reflect.Field;

public class ReflectionHelpers {

    /**
     * Reflectively get the value of a field.
     *
     * @param object Target object.
     * @param fieldName The field name.
     * @param <R> The return type.
     * @return Value of the field on the object.
     */
    public static <R> R getField(Object object, String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return (R) field.get(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reflectively set the value of a field.
     *
     * @param object Target object.
     * @param fieldName The field name.
     * @param fieldNewValue New value.
     */
    public static void setField(Object object, String fieldName, Object fieldNewValue) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, fieldNewValue);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
