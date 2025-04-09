
package com.android.systemui.unfold.dagger

import javax.inject.Qualifier

/**
 * Alternative to [UiBackground] qualifier annotation in unfold module.
 *
 * It is needed as we can't depend on SystemUI code in this module.
 */
@Qualifier @Retention(AnnotationRetention.RUNTIME) annotation class UnfoldSingleThreadBg
