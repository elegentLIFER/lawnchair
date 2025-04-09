

package com.android.systemui.dagger.qualifiers;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

/**
 * An annotation for injecting instances related to UI operations off the main-thread.
 */
@Qualifier
@Documented
@Retention(RUNTIME)
public @interface UiBackground {
}
