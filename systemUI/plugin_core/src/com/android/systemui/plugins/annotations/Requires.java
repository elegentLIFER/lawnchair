

package com.android.systemui.plugins.annotations;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Used to annotate which interfaces a given plugin depends on.
 *
 * At minimum all plugins should have at least one @Requires annotation
 * for the plugin interface that they are implementing. They will also
 * need an @Requires for each class that the plugin interface @DependsOn.
 */
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(value = Requirements.class)
public @interface Requires {
    Class<?> target();
    int version();
}
