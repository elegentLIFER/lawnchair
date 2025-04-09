

package com.android.wm.shell.onehanded;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.testing.AndroidTestingRunner;

import androidx.test.filters.SmallTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@SmallTest
@RunWith(AndroidTestingRunner.class)
public class OneHandedSettingsUtilTest extends OneHandedTestCase {
    OneHandedSettingsUtil mSettingsUtil;

    @Mock
    ContentResolver mMockContentResolver;
    @Mock
    ContentObserver mMockContentObserver;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mSettingsUtil = new OneHandedSettingsUtil();
    }

    @Test
    public void testUnregisterSecureKeyObserver() {
        mSettingsUtil.unregisterSettingsKeyObserver(mMockContentResolver, mMockContentObserver);

        verify(mMockContentResolver).unregisterContentObserver(any());
    }
}
