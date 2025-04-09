

package com.android.wm.shell.common;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.android.wm.shell.sysui.ShellInit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@SmallTest
@RunWith(AndroidJUnit4.class)
public class DevicePostureControllerTest {
    @Mock
    private Context mContext;

    @Mock
    private ShellInit mShellInit;

    @Mock
    private ShellExecutor mMainExecutor;

    @Captor
    private ArgumentCaptor<Integer> mDevicePostureCaptor;

    @Mock
    private DevicePostureController.OnDevicePostureChangedListener mOnDevicePostureChangedListener;

    private DevicePostureController mDevicePostureController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mDevicePostureController = new DevicePostureController(mContext, mShellInit, mMainExecutor);
    }

    @Test
    public void instantiateController_addInitCallback() {
        verify(mShellInit, times(1)).addInitCallback(any(), eq(mDevicePostureController));
    }

    @Test
    public void registerOnDevicePostureChangedListener_callbackCurrentPosture() {
        mDevicePostureController.registerOnDevicePostureChangedListener(
                mOnDevicePostureChangedListener);
        verify(mOnDevicePostureChangedListener, times(1))
                .onDevicePostureChanged(anyInt());
    }

    @Test
    public void onDevicePostureChanged_differentPosture_callbackListener() {
        mDevicePostureController.registerOnDevicePostureChangedListener(
                mOnDevicePostureChangedListener);
        verify(mOnDevicePostureChangedListener).onDevicePostureChanged(
                mDevicePostureCaptor.capture());
        clearInvocations(mOnDevicePostureChangedListener);

        int differentDevicePosture = mDevicePostureCaptor.getValue() + 1;
        mDevicePostureController.onDevicePostureChanged(differentDevicePosture);

        verify(mOnDevicePostureChangedListener, times(1))
                .onDevicePostureChanged(differentDevicePosture);
    }

    @Test
    public void onDevicePostureChanged_samePosture_doesNotCallbackListener() {
        mDevicePostureController.registerOnDevicePostureChangedListener(
                mOnDevicePostureChangedListener);
        verify(mOnDevicePostureChangedListener).onDevicePostureChanged(
                mDevicePostureCaptor.capture());
        clearInvocations(mOnDevicePostureChangedListener);

        int sameDevicePosture = mDevicePostureCaptor.getValue();
        mDevicePostureController.onDevicePostureChanged(sameDevicePosture);

        verifyZeroInteractions(mOnDevicePostureChangedListener);
    }
}
