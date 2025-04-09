

package com.android.wm.shell.taskview;

import android.annotation.UiContext;
import android.content.Context;

import com.android.wm.shell.shared.annotations.ExternalThread;

import java.util.concurrent.Executor;
import java.util.function.Consumer;

/** Interface to create TaskView. */
@ExternalThread
public interface TaskViewFactory {
    /** Creates an {@link TaskView} */
    void create(@UiContext Context context, Executor executor, Consumer<TaskView> onCreate);
}
