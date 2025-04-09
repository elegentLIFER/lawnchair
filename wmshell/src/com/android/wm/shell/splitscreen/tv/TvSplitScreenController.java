

package com.android.wm.shell.splitscreen.tv;

import static android.view.Display.DEFAULT_DISPLAY;

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
import com.android.wm.shell.draganddrop.DragAndDropController;
import com.android.wm.shell.recents.RecentTasksController;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.splitscreen.StageCoordinator;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;

import java.util.Optional;

/**
 * Class inherits from {@link SplitScreenController} and provides {@link TvStageCoordinator}
 * for Split Screen on TV.
 */
public class TvSplitScreenController extends SplitScreenController {
    private final ShellTaskOrganizer mTaskOrganizer;
    private final SyncTransactionQueue mSyncQueue;
    private final Context mContext;
    private final ShellExecutor mMainExecutor;
    private final DisplayController mDisplayController;
    private final DisplayImeController mDisplayImeController;
    private final DisplayInsetsController mDisplayInsetsController;
    private final Transitions mTransitions;
    private final TransactionPool mTransactionPool;
    private final IconProvider mIconProvider;
    private final Optional<RecentTasksController> mRecentTasksOptional;
    private final LaunchAdjacentController mLaunchAdjacentController;

    private final Handler mMainHandler;
    private final SystemWindows mSystemWindows;

    public TvSplitScreenController(Context context,
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
            ShellExecutor mainExecutor,
            Handler mainHandler,
            SystemWindows systemWindows) {
        super(context, shellInit, shellCommandHandler, shellController, shellTaskOrganizer,
                syncQueue, rootTDAOrganizer, displayController, displayImeController,
                displayInsetsController, null, transitions, transactionPool,
                iconProvider, recentTasks, launchAdjacentController, Optional.empty(),
                Optional.empty(), null /* stageCoordinator */, multiInstanceHelper, mainExecutor);

        mTaskOrganizer = shellTaskOrganizer;
        mSyncQueue = syncQueue;
        mContext = context;
        mMainExecutor = mainExecutor;
        mDisplayController = displayController;
        mDisplayImeController = displayImeController;
        mDisplayInsetsController = displayInsetsController;
        mTransitions = transitions;
        mTransactionPool = transactionPool;
        mIconProvider = iconProvider;
        mRecentTasksOptional = recentTasks;
        mLaunchAdjacentController = launchAdjacentController;

        mMainHandler = mainHandler;
        mSystemWindows = systemWindows;
    }

    /**
     * Provides Tv-specific StageCoordinator.
     * @return {@link TvStageCoordinator}
     */
    @Override
    protected StageCoordinator createStageCoordinator() {
        return new TvStageCoordinator(mContext, DEFAULT_DISPLAY, mSyncQueue,
                mTaskOrganizer, mDisplayController, mDisplayImeController,
                mDisplayInsetsController, mTransitions, mTransactionPool,
                mIconProvider, mMainExecutor, mMainHandler,
                mRecentTasksOptional, mLaunchAdjacentController, mSystemWindows);
    }

}
