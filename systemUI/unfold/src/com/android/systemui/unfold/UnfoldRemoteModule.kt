

package com.android.systemui.unfold

import android.os.Handler
import com.android.systemui.unfold.config.UnfoldTransitionConfig
import com.android.systemui.unfold.dagger.UnfoldMain
import com.android.systemui.unfold.dagger.UseReceivingFilter
import com.android.systemui.unfold.progress.RemoteUnfoldTransitionReceiver
import com.android.systemui.unfold.updates.RotationChangeProvider
import com.android.systemui.unfold.util.ATraceLoggerTransitionProgressListener
import dagger.Module
import dagger.Provides
import java.util.Optional
import javax.inject.Provider
import javax.inject.Singleton

/** Binds classes needed to provide unfold transition progresses to another process. */
@Module
class UnfoldRemoteModule {
    @Provides
    @Singleton
    fun provideTransitionProvider(
        config: UnfoldTransitionConfig,
        traceListener: ATraceLoggerTransitionProgressListener.Factory,
        remoteReceiverProvider: Provider<RemoteUnfoldTransitionReceiver>,
    ): Optional<RemoteUnfoldTransitionReceiver> {
        if (!config.isEnabled) {
            return Optional.empty()
        }
        val remoteReceiver = remoteReceiverProvider.get()
        remoteReceiver.addCallback(traceListener.create("remoteReceiver"))
        return Optional.of(remoteReceiver)
    }

    @Provides @UseReceivingFilter fun useReceivingFilter(): Boolean = true

    @Provides
    @UnfoldMain
    fun provideMainRotationChangeProvider(
        rotationChangeProviderFactory: RotationChangeProvider.Factory,
        @UnfoldMain callbackHandler: Handler,
    ): RotationChangeProvider {
        return rotationChangeProviderFactory.create(callbackHandler)
    }
}
