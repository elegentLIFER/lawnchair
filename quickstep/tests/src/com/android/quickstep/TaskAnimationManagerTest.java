

package com.android.quickstep;

import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;

import androidx.test.filters.SmallTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@SmallTest
public class TaskAnimationManagerTest {

    @Mock
    private Context mContext;

    @Mock
    private SystemUiProxy mSystemUiProxy;

    private TaskAnimationManager mTaskAnimationManager;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mTaskAnimationManager = new TaskAnimationManager(mContext) {
            @Override
            SystemUiProxy getSystemUiProxy() {
                return mSystemUiProxy;
            }
        };
    }

    @Test
    public void startRecentsActivity_allowBackgroundLaunch() {
        assumeTrue(TaskAnimationManager.ENABLE_SHELL_TRANSITIONS);

        final LauncherActivityInterface activityInterface = mock(LauncherActivityInterface.class);
        final GestureState gestureState = mock(GestureState.class);
        final RecentsAnimationCallbacks.RecentsAnimationListener listener =
                mock(RecentsAnimationCallbacks.RecentsAnimationListener.class);
        doReturn(activityInterface).when(gestureState).getContainerInterface();
        mTaskAnimationManager.startRecentsAnimation(gestureState, new Intent(), listener);

        final ArgumentCaptor<ActivityOptions> optionsCaptor =
                ArgumentCaptor.forClass(ActivityOptions.class);
        verify(mSystemUiProxy).startRecentsActivity(any(), optionsCaptor.capture(), any());
        assertTrue(optionsCaptor.getValue()
                .isPendingIntentBackgroundActivityLaunchAllowedByPermission());
    }
}
