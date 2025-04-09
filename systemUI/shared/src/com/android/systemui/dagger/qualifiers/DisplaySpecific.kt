
package com.android.systemui.dagger.qualifiers

import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy.RUNTIME
import javax.inject.Qualifier

/** Annotates a class that is display specific. */
@Qualifier @Documented @Retention(RUNTIME) annotation class DisplaySpecific
