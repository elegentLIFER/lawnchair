

package com.android.wm.shell.dagger;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

/**
 * Scope annotation for singleton items within the WMComponent.
 */
@Documented
@Retention(RUNTIME)
@Scope
public @interface WMSingleton {
}
