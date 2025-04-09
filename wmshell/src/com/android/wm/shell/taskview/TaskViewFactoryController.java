

package com.android.wm.shell.taskview;

import android.annotation.UiContext;
import android.content.Context;

import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.shared.annotations.ExternalThread;

import java.util.concurrent.Executor;
import java.util.function.Consumer;

/** Factory controller which can create {@link TaskView} */
public class TaskViewFactoryController {
    private final ShellTaskOrganizer mTaskOrganizer;
    private final ShellExecutor mShellExecutor;
    private final SyncTransactionQueue mSyncQueue;
    private final TaskViewTransitions mTaskViewTransitions;
    private final TaskViewFactory mImpl = new TaskViewFactoryImpl();

    public TaskViewFactoryController(ShellTaskOrganizer taskOrganizer,
            ShellExecutor shellExecutor, SyncTransactionQueue syncQueue,
            TaskViewTransitions taskViewTransitions) {
        mTaskOrganizer = taskOrganizer;
        mShellExecutor = shellExecutor;
        mSyncQueue = syncQueue;
        mTaskViewTransitions = taskViewTransitions;
    }

    public TaskViewFactoryController(ShellTaskOrganizer taskOrganizer,
            ShellExecutor shellExecutor, SyncTransactionQueue syncQueue) {
        mTaskOrganizer = taskOrganizer;
        mShellExecutor = shellExecutor;
        mSyncQueue = syncQueue;
        mTaskViewTransitions = null;
    }

    /**
     * @return the underlying {@link TaskViewFactory}.
     */
    public TaskViewFactory asTaskViewFactory() {
        return mImpl;
    }

    /** Creates an {@link TaskView} */
    public void create(@UiContext Context context, Executor executor, Consumer<TaskView> onCreate) {
        TaskView taskView = new TaskView(context, new TaskViewTaskController(context,
                mTaskOrganizer, mTaskViewTransitions, mSyncQueue));
        executor.execute(() -> {
            onCreate.accept(taskView);
        });
    }

    private class TaskViewFactoryImpl implements TaskViewFactory {
        @ExternalThread
        public void create(@UiContext Context context,
                Executor executor, Consumer<TaskView> onCreate) {
            mShellExecutor.execute(() -> {
                TaskViewFactoryController.this.create(context, executor, onCreate);
            });
        }
    }
}
