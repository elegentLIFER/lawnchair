

package com.android.launcher3.logging;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(SOURCE)
@Target(FIELD)
//  Copy of frameworks/base/core/java/com/android/internal/logging/UiEvent.java
public @interface UiEvent {

    /**
     * An explanation, suitable for Android analysts, of the UI event that this log represents.
     */
    String doc();
}
