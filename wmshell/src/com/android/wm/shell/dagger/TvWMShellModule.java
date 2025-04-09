

package com.android.wm.shell.dagger;

import android.content.Context;
import android.os.Handler;

import com.android.launcher3.icons.IconProvider;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayImeController;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.common.LaunchAdjacentController;
import com.android.wm.shell.common.MultiInstanceHelper;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.common.SystemWindows;
import com.android.wm.shell.common.TransactionPool;
import com.android.wm.shell.dagger.pip.TvPipModule;
import com.android.wm.shell.recents.RecentTasksController;
import com.android.wm.shell.shared.annotations.ShellMainThread;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.splitscreen.tv.TvSplitScreenController;
import com.android.wm.shell.startingsurface.StartingWindowTypeAlgorithm;
import com.android.wm.shell.startingsurface.tv.TvStartingWindowTypeAlgorithm;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;

import dagger.Module;
import dagger.Provides;

import java.util.Optional;

/**
 * Provides dependencies from {@link com.android.wm.shell}, these dependencies are only accessible
 * from components within the WM subcomponent (can be explicitly exposed to the SysUIComponent, see
 * {@link com.android.systemui.dagger.WMComponent}).
 *
 * <p>This module only defines Shell dependencies for the TV SystemUI implementation. Common
 * dependencies should go into {@link WMShellBaseModule}.
 */
@Module(includes = {TvPipModule.class})
public class TvWMShellModule {

    //
    // Starting Windows (Splash Screen)
    //

    @WMSingleton
    @Provides
    @DynamicOverride
    static StartingWindowTypeAlgorithm provideStartingWindowTypeAlgorithm() {
        return new TvStartingWindowTypeAlgorithm();
    }

    @WMSingleton
    @Provides
    @DynamicOverride
    static SplitScreenController provideSplitScreenController(Context context,
            ShellInit shellInit,
            ShellCommandHandler shellCommandHandler,
            ShellController shellController,
            ShellTaskOrganizer shellTaskOrganizer,
            SyncTransactionQueue syncQueue,
            RootTaskDisplayAreaOrganizer rootTDAOrganizer,
            DisplayController displayController,
            DisplayImeController displayImeController,
            DisplayInsetsController displayInsetsController,
            Transitions transitions,
            TransactionPool transactionPool,
            IconProvider iconProvider,
            Optional<RecentTasksController> recentTasks,
            LaunchAdjacentController launchAdjacentController,
            MultiInstanceHelper multiInstanceHelper,
            @ShellMainThread ShellExecutor mainExecutor,
            Handler mainHandler,
            SystemWindows systemWindows) {
        return new TvSplitScreenController(context, shellInit, shellCommandHandler, shellController,
                shellTaskOrganizer, syncQueue, rootTDAOrganizer, displayController,
                displayImeController, displayInsetsController, transitions, transactionPool,
                iconProvider, recentTasks, launchAdjacentController, multiInstanceHelper,
                mainExecutor, mainHandler, systemWindows);
    }
}
