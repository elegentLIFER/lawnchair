

package com.android.wm.shell.splitscreen;

import static android.app.ActivityTaskManager.INVALID_TASK_ID;
import static android.view.Display.DEFAULT_DISPLAY;

import static com.google.common.truth.Truth.assertThat;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import android.app.ActivityManager;
import android.os.SystemProperties;
import android.view.SurfaceControl;
import android.view.SurfaceSession;
import android.window.WindowContainerTransaction;

import androidx.test.annotation.UiThreadTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.android.launcher3.icons.IconProvider;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.ShellTestCase;
import com.android.wm.shell.TestRunningTaskInfoBuilder;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.windowdecor.WindowDecorViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

/**
 * Tests for {@link StageTaskListener}
 * Build/Install/Run:
 * atest WMShellUnitTests:StageTaskListenerTests
 */
@SmallTest
@RunWith(AndroidJUnit4.class)
public final class StageTaskListenerTests extends ShellTestCase {
    private static final boolean ENABLE_SHELL_TRANSITIONS =
            SystemProperties.getBoolean("persist.wm.debug.shell_transit", true);

    @Mock
    private ShellTaskOrganizer mTaskOrganizer;
    @Mock
    private StageTaskListener.StageListenerCallbacks mCallbacks;
    @Mock
    private SyncTransactionQueue mSyncQueue;
    @Mock
    private IconProvider mIconProvider;
    @Mock
    private WindowDecorViewModel mWindowDecorViewModel;
    @Captor
    private ArgumentCaptor<SyncTransactionQueue.TransactionRunnable> mRunnableCaptor;
    private SurfaceSession mSurfaceSession = new SurfaceSession();
    private SurfaceControl mSurfaceControl;
    private ActivityManager.RunningTaskInfo mRootTask;
    private StageTaskListener mStageTaskListener;

    @Before
    @UiThreadTest
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mStageTaskListener = new StageTaskListener(
                mContext,
                mTaskOrganizer,
                DEFAULT_DISPLAY,
                mCallbacks,
                mSyncQueue,
                mSurfaceSession,
                mIconProvider,
                Optional.of(mWindowDecorViewModel));
        mRootTask = new TestRunningTaskInfoBuilder().build();
        mRootTask.parentTaskId = INVALID_TASK_ID;
        mSurfaceControl = new SurfaceControl.Builder(mSurfaceSession).setName("test").build();
        mStageTaskListener.onTaskAppeared(mRootTask, mSurfaceControl);
    }

    @Test
    public void testInitsDimLayer() {
        verify(mSyncQueue).runInSync(mRunnableCaptor.capture());
        final SurfaceControl.Transaction t = new SurfaceControl.Transaction();
        mRunnableCaptor.getValue().runWithTransaction(t);
        t.apply();

        assertThat(mStageTaskListener.mDimLayer).isNotNull();
    }

    @Test
    public void testRootTaskAppeared() {
        assertThat(mStageTaskListener.mRootTaskInfo.taskId).isEqualTo(mRootTask.taskId);
        verify(mCallbacks).onRootTaskAppeared();
        verify(mCallbacks).onStatusChanged(eq(mRootTask.isVisible), eq(false));
    }

    @Test
    public void testChildTaskAppeared() {
        // With shell transitions, the transition manages status changes, so skip this test.
        assumeFalse(ENABLE_SHELL_TRANSITIONS);
        final ActivityManager.RunningTaskInfo childTask =
                new TestRunningTaskInfoBuilder().setParentTaskId(mRootTask.taskId).build();

        mStageTaskListener.onTaskAppeared(childTask, mSurfaceControl);

        assertThat(mStageTaskListener.mChildrenTaskInfo.contains(childTask.taskId)).isTrue();
        verify(mCallbacks).onStatusChanged(eq(mRootTask.isVisible), eq(true));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnknownTaskVanished() {
        final ActivityManager.RunningTaskInfo task = new TestRunningTaskInfoBuilder().build();
        mStageTaskListener.onTaskVanished(task);
    }

    @Test
    public void testTaskVanished() {
        // With shell transitions, the transition manages status changes, so skip this test.
        assumeFalse(ENABLE_SHELL_TRANSITIONS);
        final ActivityManager.RunningTaskInfo childTask =
                new TestRunningTaskInfoBuilder().setParentTaskId(mRootTask.taskId).build();
        mStageTaskListener.mRootTaskInfo = mRootTask;
        mStageTaskListener.mChildrenTaskInfo.put(childTask.taskId, childTask);

        mStageTaskListener.onTaskVanished(childTask);
        verify(mCallbacks, times(2)).onStatusChanged(eq(mRootTask.isVisible), eq(false));

        mStageTaskListener.onTaskVanished(mRootTask);
        verify(mCallbacks).onRootTaskVanished();
    }

    @Test
    public void testTaskInfoChanged_notSupportsMultiWindow() {
        final ActivityManager.RunningTaskInfo childTask =
                new TestRunningTaskInfoBuilder().setParentTaskId(mRootTask.taskId).build();
        childTask.supportsMultiWindow = false;

        mStageTaskListener.onTaskInfoChanged(childTask);
        verify(mCallbacks).onNoLongerSupportMultiWindow(childTask);
    }

    @Test
    public void testEvictAllChildren() {
        final WindowContainerTransaction wct = new WindowContainerTransaction();
        mStageTaskListener.evictAllChildren(wct);
        assertTrue(wct.isEmpty());

        final ActivityManager.RunningTaskInfo childTask =
                new TestRunningTaskInfoBuilder().setParentTaskId(mRootTask.taskId).build();
        mStageTaskListener.onTaskAppeared(childTask, mSurfaceControl);

        mStageTaskListener.evictAllChildren(wct);
        assertFalse(wct.isEmpty());
    }
}
