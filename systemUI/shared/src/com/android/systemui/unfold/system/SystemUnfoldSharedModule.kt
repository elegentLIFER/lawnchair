
package com.android.systemui.unfold.system

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Process
import com.android.systemui.dagger.qualifiers.Main
import com.android.systemui.dagger.qualifiers.UiBackground
import com.android.systemui.unfold.config.ResourceUnfoldTransitionConfig
import com.android.systemui.unfold.config.UnfoldTransitionConfig
import com.android.systemui.unfold.dagger.UnfoldBg
import com.android.systemui.unfold.dagger.UnfoldMain
import com.android.systemui.unfold.dagger.UnfoldSingleThreadBg
import com.android.systemui.unfold.updates.FoldProvider
import com.android.systemui.unfold.util.CurrentActivityTypeProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.android.asCoroutineDispatcher

/**
 * Dagger module with system-only dependencies for the unfold animation. The code that is used to
 * calculate unfold transition progress depends on some hidden APIs that are not available in normal
 * apps. In order to re-use this code and use alternative implementations of these classes in other
 * apps and hidden APIs here.
 */
@Module
abstract class SystemUnfoldSharedModule {

    @Binds
    abstract fun activityTypeProvider(executor: ActivityManagerActivityTypeProvider):
            CurrentActivityTypeProvider

    @Binds
    abstract fun config(config: ResourceUnfoldTransitionConfig): UnfoldTransitionConfig

    @Binds
    abstract fun foldState(provider: DeviceStateManagerFoldProvider): FoldProvider

    @Binds
    abstract fun deviceStateRepository(provider: DeviceStateRepositoryImpl): DeviceStateRepository

    @Binds
    @UnfoldMain
    abstract fun mainExecutor(@Main executor: Executor): Executor

    @Binds
    @UnfoldMain
    abstract fun mainHandler(@Main handler: Handler): Handler

    @Binds
    @UnfoldSingleThreadBg
    abstract fun backgroundExecutor(@UiBackground executor: Executor): Executor

    companion object {
        @Provides
        @UnfoldBg
        @Singleton
        fun unfoldBgProgressHandler(@UnfoldBg looper: Looper): Handler {
            return Handler(looper)
        }

        @Provides
        @UnfoldBg
        @Singleton
        fun unfoldBgDispatcher(@UnfoldBg handler: Handler): CoroutineDispatcher {
            return handler.asCoroutineDispatcher("@UnfoldBg Dispatcher")
        }

        @Provides
        @UnfoldBg
        @Singleton
        fun provideBgLooper(): Looper {
            return HandlerThread("UnfoldBg", Process.THREAD_PRIORITY_FOREGROUND)
                .apply { start() }
                .looper
        }
    }
}
