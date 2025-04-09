

package com.android.wm.shell.compatui;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import android.app.TaskInfo;
import android.testing.AndroidTestingRunner;
import android.testing.TestableLooper;
import android.view.LayoutInflater;
import android.view.View;

import androidx.test.filters.SmallTest;

import com.android.wm.shell.R;
import com.android.wm.shell.ShellTestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Tests for {@link LetterboxEduDialogLayout}.
 *
 * Build/Install/Run:
 * atest WMShellUnitTests:ReachabilityEduLayoutTest
 */
@RunWith(AndroidTestingRunner.class)
@SmallTest
@TestableLooper.RunWithLooper(setAsMainLooper = true)
public class ReachabilityEduLayoutTest extends ShellTestCase {

    private ReachabilityEduLayout mLayout;
    private View mMoveUpButton;
    private View mMoveDownButton;
    private View mMoveLeftButton;
    private View mMoveRightButton;

    @Mock
    private CompatUIConfiguration mCompatUIConfiguration;

    @Mock
    private TaskInfo mTaskInfo;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mLayout = (ReachabilityEduLayout) LayoutInflater.from(mContext)
                .inflate(R.layout.reachability_ui_layout, null);
        mMoveLeftButton = mLayout.findViewById(R.id.reachability_move_left_button);
        mMoveRightButton = mLayout.findViewById(R.id.reachability_move_right_button);
        mMoveUpButton = mLayout.findViewById(R.id.reachability_move_up_button);
        mMoveDownButton = mLayout.findViewById(R.id.reachability_move_down_button);
    }

    @Test
    public void testOnFinishInflate() {
        assertNotNull(mMoveUpButton);
        assertNotNull(mMoveDownButton);
        assertNotNull(mMoveLeftButton);
        assertNotNull(mMoveRightButton);
    }

    @Test
    public void handleVisibility_educationNotEnabled_buttonsAreHidden() {
        mLayout.handleVisibility(/* horizontalEnabled */ false, /* verticalEnabled */
                false, /* letterboxVerticalPosition */
                -1, /* letterboxHorizontalPosition */ -1, /* availableWidth */
                0, /* availableHeight */ 0, mCompatUIConfiguration, mTaskInfo);
        assertEquals(View.INVISIBLE, mMoveUpButton.getVisibility());
        assertEquals(View.INVISIBLE, mMoveDownButton.getVisibility());
        assertEquals(View.INVISIBLE, mMoveLeftButton.getVisibility());
        assertEquals(View.INVISIBLE, mMoveRightButton.getVisibility());
    }

    @Test
    public void handleVisibility_horizontalEducationEnableduiConfigurationIsUpdated() {
        mLayout.handleVisibility(/* horizontalEnabled */ true, /* verticalEnabled */
                false, /* letterboxVerticalPosition */ -1, /* letterboxHorizontalPosition */
                1, /* availableWidth */ 500, /* availableHeight */ 0, mCompatUIConfiguration,
                mTaskInfo);

        verify(mCompatUIConfiguration).setUserHasSeenHorizontalReachabilityEducation(mTaskInfo);
        verify(mCompatUIConfiguration, never()).setUserHasSeenVerticalReachabilityEducation(
                mTaskInfo);
    }

    @Test
    public void handleVisibility_verticalEducationEnabled_uiConfigurationIsUpdated() {
        mLayout.handleVisibility(/* horizontalEnabled */ false, /* verticalEnabled */
                true, /* letterboxVerticalPosition */ 0, /* letterboxHorizontalPosition */
                -1, /* availableWidth */ 0, /* availableHeight */ 500, mCompatUIConfiguration,
                mTaskInfo);

        verify(mCompatUIConfiguration, never())
                .setUserHasSeenHorizontalReachabilityEducation(mTaskInfo);
        verify(mCompatUIConfiguration).setUserHasSeenVerticalReachabilityEducation(mTaskInfo);
    }
}
