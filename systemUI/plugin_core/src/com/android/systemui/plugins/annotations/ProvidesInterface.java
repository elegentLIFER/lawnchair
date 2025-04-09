

package com.android.systemui.plugins.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Should be added to all interfaces in plugin lib to specify their
 * current version and optionally their action to implement the plugin.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ProvidesInterface {
    int version();

    String action() default "";

}
