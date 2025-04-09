

package com.android.wm.shell.windowdecor.additionalviewcontainer

import android.content.Context
import android.testing.AndroidTestingRunner
import android.testing.TestableLooper
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.test.filters.SmallTest
import com.android.wm.shell.R
import com.android.wm.shell.ShellTestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

/**
 * Tests for [AdditionalSystemViewContainer].
 *
 * Build/Install/Run:
 * atest WMShellUnitTests:AdditionalSystemViewContainerTest
 */
@SmallTest
@TestableLooper.RunWithLooper
@RunWith(AndroidTestingRunner::class)
class AdditionalSystemViewContainerTest : ShellTestCase() {
    @Mock
    private lateinit var mockView: View
    @Mock
    private lateinit var mockLayoutInflater: LayoutInflater
    @Mock
    private lateinit var mockContext: Context
    @Mock
    private lateinit var mockWindowManager: WindowManager
    private lateinit var viewContainer: AdditionalSystemViewContainer

    @Before
    fun setUp() {
        whenever(mockContext.getSystemService(WindowManager::class.java))
            .thenReturn(mockWindowManager)
        whenever(mockContext.getSystemService(Context
            .LAYOUT_INFLATER_SERVICE)).thenReturn(mockLayoutInflater)
        whenever(mockLayoutInflater.inflate(
            R.layout.desktop_mode_window_decor_handle_menu, null)).thenReturn(mockView)
    }

    @Test
    fun testReleaseView_ViewRemoved() {
        viewContainer = AdditionalSystemViewContainer(
            mockContext,
            R.layout.desktop_mode_window_decor_handle_menu,
            TASK_ID,
            X,
            Y,
            WIDTH,
            HEIGHT
        )
        verify(mockWindowManager).addView(eq(mockView), any())
        viewContainer.releaseView()
        verify(mockWindowManager).removeViewImmediate(mockView)
    }

    companion object {
        private const val X = 500
        private const val Y = 50
        private const val WIDTH = 400
        private const val HEIGHT = 600
        private const val TASK_ID = 5
    }
}
