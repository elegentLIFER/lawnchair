
package com.android.systemui.unfold.dagger

import javax.inject.Qualifier

/** Qualifier annotation for a progress provider that emits animation events only when
 * in natural rotation */
@Qualifier @Retention(AnnotationRetention.RUNTIME) annotation class NaturalRotation
