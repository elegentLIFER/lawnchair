

package com.android.systemui.plugins.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Used for repeated @Requires internally, not for plugin
 * use.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Requirements {
    Requires[] value();
}
