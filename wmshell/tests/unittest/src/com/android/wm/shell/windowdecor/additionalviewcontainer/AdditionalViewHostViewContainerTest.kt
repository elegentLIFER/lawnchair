

package com.android.wm.shell.windowdecor.additionalviewcontainer

import android.testing.AndroidTestingRunner
import android.view.SurfaceControl
import android.view.SurfaceControlViewHost
import androidx.test.filters.SmallTest
import com.android.wm.shell.ShellTestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.function.Supplier

/**
 * Tests for [AdditionalViewHostViewContainer].
 *
 * Build/Install/Run:
 * atest WMShellUnitTests:AdditionalViewHostViewContainerTest
 */
@SmallTest
@RunWith(AndroidTestingRunner::class)
class AdditionalViewHostViewContainerTest : ShellTestCase() {
    @Mock
    private lateinit var mockTransactionSupplier: Supplier<SurfaceControl.Transaction>
    @Mock
    private lateinit var mockTransaction: SurfaceControl.Transaction
    @Mock
    private lateinit var mockSurface: SurfaceControl
    @Mock
    private lateinit var mockViewHost: SurfaceControlViewHost
    private lateinit var viewContainer: AdditionalViewHostViewContainer

    @Before
    fun setUp() {
        whenever(mockTransactionSupplier.get()).thenReturn(mockTransaction)
    }

    @Test
    fun testReleaseView_ViewRemoved() {
        viewContainer = AdditionalViewHostViewContainer(
            mockSurface,
            mockViewHost,
            mockTransactionSupplier
        )
        viewContainer.releaseView()
        verify(mockViewHost).release()
        verify(mockTransaction).remove(mockSurface)
        verify(mockTransaction).apply()
    }
}
