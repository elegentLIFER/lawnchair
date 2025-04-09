

package com.android.systemui.plugins.annotations;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Used to indicate that an interface in the plugin library needs another
 * interface to function properly. When this is added, it will be enforced
 * that all plugins that @Requires the annotated interface also @Requires
 * the specified class as well.
 */
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(value = Dependencies.class)
public @interface DependsOn {
    Class<?> target();

}
