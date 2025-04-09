

package com.android.systemui.unfold.dagger

import javax.inject.Qualifier

/**
 * Annotates the boolean representing whether we are calculating progresses in the background.
 *
 * Used to allow clients to provide this value, without depending on the flags directly.
 */
@Qualifier @Retention(AnnotationRetention.RUNTIME) annotation class UnfoldBgProgressFlag
