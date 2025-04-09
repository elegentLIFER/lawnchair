

package com.android.wm.shell.common;

import static android.view.Display.DEFAULT_DISPLAY;
import static android.view.InsetsSource.ID_IME;
import static android.view.Surface.ROTATION_0;
import static android.view.WindowInsets.Type.ime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import android.graphics.Insets;
import android.graphics.Point;
import android.view.InsetsSource;
import android.view.InsetsSourceControl;
import android.view.InsetsState;
import android.view.SurfaceControl;
import android.view.inputmethod.ImeTracker;

import androidx.test.filters.SmallTest;

import com.android.wm.shell.ShellTestCase;
import com.android.wm.shell.sysui.ShellInit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.Executor;

/**
 * Tests for the display IME controller.
 *
 * <p> Build/Install/Run:
 *  atest WMShellUnitTests:DisplayImeControllerTest
 */
@SmallTest
public class DisplayImeControllerTest extends ShellTestCase {

    @Mock
    private SurfaceControl.Transaction mT;
    @Mock
    private ShellInit mShellInit;
    private DisplayImeController.PerDisplay mPerDisplay;
    private Executor mExecutor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mExecutor = spy(Runnable::run);
        mPerDisplay = new DisplayImeController(null, mShellInit, null, null, new TransactionPool() {
            @Override
            public SurfaceControl.Transaction acquire() {
                return mT;
            }

            @Override
            public void release(SurfaceControl.Transaction t) {
            }
        }, mExecutor) {
            @Override
            void removeImeSurface() { }
        }.new PerDisplay(DEFAULT_DISPLAY, ROTATION_0);
    }

    @Test
    public void instantiateController_addInitCallback() {
        verify(mShellInit, times(1)).addInitCallback(any(), any());
    }

    @Test
    public void insetsControlChanged_schedulesNoWorkOnExecutor() {
        mPerDisplay.insetsControlChanged(insetsStateWithIme(false), insetsSourceControl());
        verifyZeroInteractions(mExecutor);
    }

    @Test
    public void insetsChanged_schedulesNoWorkOnExecutor() {
        mPerDisplay.insetsChanged(insetsStateWithIme(false));
        verifyZeroInteractions(mExecutor);
    }

    @Test
    public void showInsets_schedulesNoWorkOnExecutor() {
        mPerDisplay.showInsets(ime(), true /* fromIme */, ImeTracker.Token.empty());
        verifyZeroInteractions(mExecutor);
    }

    @Test
    public void hideInsets_schedulesNoWorkOnExecutor() {
        mPerDisplay.hideInsets(ime(), true /* fromIme */, ImeTracker.Token.empty());
        verifyZeroInteractions(mExecutor);
    }

    @Test
    public void reappliesVisibilityToChangedLeash() {
        verifyZeroInteractions(mT);
        mPerDisplay.mImeShowing = true;

        mPerDisplay.insetsControlChanged(insetsStateWithIme(false), insetsSourceControl());

        assertFalse(mPerDisplay.mImeShowing);
        verify(mT).hide(any());

        mPerDisplay.mImeShowing = true;
        mPerDisplay.insetsControlChanged(insetsStateWithIme(true), insetsSourceControl());

        assertTrue(mPerDisplay.mImeShowing);
        verify(mT).show(any());
    }

    @Test
    public void insetsControlChanged_updateImeSourceControl() {
        mPerDisplay.insetsControlChanged(insetsStateWithIme(false), insetsSourceControl());
        assertNotNull(mPerDisplay.mImeSourceControl);

        mPerDisplay.insetsControlChanged(new InsetsState(), new InsetsSourceControl[]{});
        assertNull(mPerDisplay.mImeSourceControl);
    }

    private InsetsSourceControl[] insetsSourceControl() {
        return new InsetsSourceControl[]{
                new InsetsSourceControl(
                        ID_IME, ime(), mock(SurfaceControl.class), false, new Point(0, 0),
                        Insets.NONE)
        };
    }

    private InsetsState insetsStateWithIme(boolean visible) {
        InsetsState state = new InsetsState();
        state.addSource(new InsetsSource(ID_IME, ime()));
        state.setSourceVisible(ID_IME, visible);
        return state;
    }

}
