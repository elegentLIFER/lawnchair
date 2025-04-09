

package com.android.wm.shell.startingsurface;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.view.Display;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.android.internal.util.function.TriConsumer;
import com.android.launcher3.icons.IconProvider;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.ShellTestCase;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.TransactionPool;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.sysui.ShellSharedConstants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Tests for the starting window controller.
 *
 * Build/Install/Run:
 *  atest WMShellUnitTests:StartingWindowControllerTests
 */
@SmallTest
@RunWith(AndroidJUnit4.class)
public class StartingWindowControllerTests extends ShellTestCase {

    private @Mock Context mContext;
    private @Mock DisplayManager mDisplayManager;
    private @Mock DisplayInsetsController mDisplayInsetsController;
    private @Mock ShellCommandHandler mShellCommandHandler;
    private @Mock ShellTaskOrganizer mTaskOrganizer;
    private @Mock ShellExecutor mMainExecutor;
    private @Mock StartingWindowTypeAlgorithm mTypeAlgorithm;
    private @Mock IconProvider mIconProvider;
    private @Mock TransactionPool mTransactionPool;
    private StartingWindowController mController;
    private ShellInit mShellInit;
    private ShellController mShellController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        doReturn(mock(Display.class)).when(mDisplayManager).getDisplay(anyInt());
        doReturn(mDisplayManager).when(mContext).getSystemService(eq(DisplayManager.class));
        doReturn(super.mContext.getResources()).when(mContext).getResources();
        mShellInit = spy(new ShellInit(mMainExecutor));
        mShellController = spy(new ShellController(mContext, mShellInit, mShellCommandHandler,
                mDisplayInsetsController, mMainExecutor));
        mController = new StartingWindowController(mContext, mShellInit, mShellController,
                mTaskOrganizer, mMainExecutor, mTypeAlgorithm, mIconProvider, mTransactionPool);
        mShellInit.init();
    }

    @Test
    public void instantiateController_addInitCallback() {
        verify(mShellInit, times(1)).addInitCallback(any(), isA(StartingWindowController.class));
    }

    @Test
    public void instantiateController_addExternalInterface() {
        verify(mShellController, times(1)).addExternalInterface(
                eq(ShellSharedConstants.KEY_EXTRA_SHELL_STARTING_WINDOW), any(), any());
    }

    @Test
    public void testInvalidateExternalInterface_unregistersListener() {
        mController.setStartingWindowListener(new TriConsumer<Integer, Integer, Integer>() {
            @Override
            public void accept(Integer integer, Integer integer2, Integer integer3) {}
        });
        assertTrue(mController.hasStartingWindowListener());
        // Create initial interface
        mShellController.createExternalInterfaces(new Bundle());
        // Recreate the interface to trigger invalidation of the previous instance
        mShellController.createExternalInterfaces(new Bundle());
        assertFalse(mController.hasStartingWindowListener());
    }
}
