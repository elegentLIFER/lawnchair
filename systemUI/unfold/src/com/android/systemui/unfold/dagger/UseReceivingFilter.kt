
package com.android.systemui.unfold.dagger

import javax.inject.Qualifier

/** Annotates whether to use a filter in [RemoteUnfoldTransitionReceiver]. */
@Qualifier @Retention(AnnotationRetention.RUNTIME) annotation class UseReceivingFilter
