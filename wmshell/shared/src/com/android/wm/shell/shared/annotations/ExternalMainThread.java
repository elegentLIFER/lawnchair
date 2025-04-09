

package com.android.wm.shell.shared.annotations;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

/**
 * Annotates a method or qualifies a provider that runs on the main-thread of the process using
 * this library.
 */
@Qualifier
@Documented
@Retention(RUNTIME)
public @interface ExternalMainThread {
}
