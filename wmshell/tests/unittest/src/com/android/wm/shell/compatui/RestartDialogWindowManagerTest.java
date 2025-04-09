

package com.android.wm.shell.compatui;

import android.app.ActivityManager;
import android.app.TaskInfo;
import android.content.res.Configuration;
import android.testing.AndroidTestingRunner;
import android.util.Pair;

import androidx.test.filters.SmallTest;

import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.ShellTestCase;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.transition.Transitions;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.function.Consumer;

/**
 * Tests for {@link RestartDialogWindowManager}.
 *
 * Build/Install/Run:
 *  atest WMShellUnitTests:RestartDialogWindowManagerTest
 */
@RunWith(AndroidTestingRunner.class)
@SmallTest
public class RestartDialogWindowManagerTest extends ShellTestCase {

    @Mock
    private SyncTransactionQueue mSyncTransactionQueue;
    @Mock private ShellTaskOrganizer.TaskListener mTaskListener;
    @Mock private CompatUIConfiguration mCompatUIConfiguration;
    @Mock private Transitions mTransitions;
    @Mock private  Consumer<Pair<TaskInfo, ShellTaskOrganizer.TaskListener>> mOnRestartCallback;
    @Mock private  Consumer<Pair<TaskInfo, ShellTaskOrganizer.TaskListener>> mOnDismissCallback;
    private RestartDialogWindowManager mWindowManager;
    private TaskInfo mTaskInfo;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mTaskInfo = new ActivityManager.RunningTaskInfo();
        mTaskInfo.configuration.uiMode =
                (mTaskInfo.configuration.uiMode & ~Configuration.UI_MODE_NIGHT_MASK)
                        | Configuration.UI_MODE_NIGHT_NO;
        mTaskInfo.configuration.uiMode =
                (mTaskInfo.configuration.uiMode & ~Configuration.UI_MODE_TYPE_MASK)
                        | Configuration.UI_MODE_TYPE_NORMAL;
        mWindowManager = new RestartDialogWindowManager(mContext, mTaskInfo, mSyncTransactionQueue,
                mTaskListener, new DisplayLayout(), mTransitions, mOnRestartCallback,
                mOnDismissCallback, mCompatUIConfiguration);
    }

    @Test
    public void testWhenDockedStateHasChanged_needsToBeRecreated() {
        ActivityManager.RunningTaskInfo newTaskInfo = new ActivityManager.RunningTaskInfo();
        newTaskInfo.configuration.uiMode =
                (newTaskInfo.configuration.uiMode & ~Configuration.UI_MODE_TYPE_MASK)
                        | Configuration.UI_MODE_TYPE_DESK;

        Assert.assertTrue(mWindowManager.needsToBeRecreated(newTaskInfo, mTaskListener));
    }

    @Test
    public void testWhenDarkLightThemeHasChanged_needsToBeRecreated() {
        ActivityManager.RunningTaskInfo newTaskInfo = new ActivityManager.RunningTaskInfo();
        mTaskInfo.configuration.uiMode =
                (mTaskInfo.configuration.uiMode & ~Configuration.UI_MODE_NIGHT_MASK)
                        | Configuration.UI_MODE_NIGHT_YES;

        Assert.assertTrue(mWindowManager.needsToBeRecreated(newTaskInfo, mTaskListener));
    }
}
