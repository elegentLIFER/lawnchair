

package com.android.wm.shell.dagger;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * An annotation for non-base modules to specifically mark the provider that is triggering the
 * creation of independent shell components that are not created as a part of the dependencies for
 * interfaces passed to SysUI.
 *
 * TODO: This will be removed once we have a more explicit method for specifying components to start
 *       with SysUI
 */
@Documented
@Inherited
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ShellCreateTriggerOverride {}
