

package com.android.wm.shell.unfold.qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Indicates that this class is used for the shell unfold transition
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface UnfoldShellTransition {}
