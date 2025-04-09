

package com.android.systemui.plugins.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Used for repeated @DependsOn internally, not for plugin
 * use.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Dependencies {
    DependsOn[] value();
}
