
package com.android.quickstep

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.android.launcher3.FakeInvariantDeviceProfileTest
import com.android.quickstep.util.TaskCornerRadius
import com.android.quickstep.views.TaskView.FullscreenDrawParams
import com.android.systemui.shared.system.QuickStepContract
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy

/** Test for FullscreenDrawParams class. */
@SmallTest
@RunWith(AndroidJUnit4::class)
class FullscreenDrawParamsTest : FakeInvariantDeviceProfileTest() {

    private lateinit var params: FullscreenDrawParams

    @Before
    fun setup() {
        params = FullscreenDrawParams(context)
    }

    @Test
    fun setStartProgress_correctCornerRadiusForTablet() {
        initializeVarsForTablet()

        params.setProgress(
            /* fullscreenProgress= */ 0f,
            /* parentScale= */ 1.0f,
            /* taskViewScale= */ 1.0f
        )

        val expectedRadius = TaskCornerRadius.get(context)
        assertThat(params.currentDrawnCornerRadius).isEqualTo(expectedRadius)
    }

    @Test
    fun setFullProgress_correctCornerRadiusForTablet() {
        initializeVarsForTablet()

        params.setProgress(
            /* fullscreenProgress= */ 1.0f,
            /* parentScale= */ 1.0f,
            /* taskViewScale= */ 1.0f
        )

        val expectedRadius = QuickStepContract.getWindowCornerRadius(context)
        assertThat(params.currentDrawnCornerRadius).isEqualTo(expectedRadius)
    }

    @Test
    fun setStartProgress_correctCornerRadiusForPhone() {
        initializeVarsForPhone()

        params.setProgress(
            /* fullscreenProgress= */ 0f,
            /* parentScale= */ 1.0f,
            /* taskViewScale= */ 1.0f
        )

        val expectedRadius = TaskCornerRadius.get(context)
        assertThat(params.currentDrawnCornerRadius).isEqualTo(expectedRadius)
    }

    @Test
    fun setFullProgress_correctCornerRadiusForPhone() {
        initializeVarsForPhone()

        params.setProgress(
            /* fullscreenProgress= */ 1.0f,
            /* parentScale= */ 1.0f,
            /* taskViewScale= */ 1.0f
        )

        val expectedRadius = QuickStepContract.getWindowCornerRadius(context)
        assertThat(params.currentDrawnCornerRadius).isEqualTo(expectedRadius)
    }

    @Test
    fun setStartProgress_correctCornerRadiusForMultiDisplay() {
        val display1Context = context
        val display2Context = mock(Context::class.java)
        val spyParams = spy(params)

        val display1TaskRadius = TaskCornerRadius.get(display1Context)
        val display1WindowRadius = QuickStepContract.getWindowCornerRadius(display1Context)
        val display2TaskRadius = display1TaskRadius * 2 + 1 // Arbitrarily different.
        val display2WindowRadius = display1WindowRadius * 2 + 1 // Arbitrarily different.
        doReturn(display2TaskRadius).`when`(spyParams).computeTaskCornerRadius(display2Context)
        doReturn(display2WindowRadius).`when`(spyParams).computeWindowCornerRadius(display2Context)

        spyParams.updateCornerRadius(display1Context)
        spyParams.setProgress(
            /* fullscreenProgress= */ 0f,
            /* parentScale= */ 1.0f,
            /* taskViewScale= */ 1.0f
        )
        assertThat(spyParams.currentDrawnCornerRadius).isEqualTo(display1TaskRadius)

        spyParams.updateCornerRadius(display2Context)
        spyParams.setProgress(
            /* fullscreenProgress= */ 0f,
            /* parentScale= */ 1.0f,
            /* taskViewScale= */ 1.0f
        )
        assertThat(spyParams.currentDrawnCornerRadius).isEqualTo(display2TaskRadius)
    }

    @Test
    fun setFullProgress_correctCornerRadiusForMultiDisplay() {
        val display1Context = context
        val display2Context = mock(Context::class.java)
        val spyParams = spy(params)

        val display1TaskRadius = TaskCornerRadius.get(display1Context)
        val display1WindowRadius = QuickStepContract.getWindowCornerRadius(display1Context)
        val display2TaskRadius = display1TaskRadius * 2 + 1 // Arbitrarily different.
        val display2WindowRadius = display1WindowRadius * 2 + 1 // Arbitrarily different.
        doReturn(display2TaskRadius).`when`(spyParams).computeTaskCornerRadius(display2Context)
        doReturn(display2WindowRadius).`when`(spyParams).computeWindowCornerRadius(display2Context)

        spyParams.updateCornerRadius(display1Context)
        spyParams.setProgress(
            /* fullscreenProgress= */ 1.0f,
            /* parentScale= */ 1.0f,
            /* taskViewScale= */ 1.0f
        )
        assertThat(spyParams.currentDrawnCornerRadius).isEqualTo(display1WindowRadius)

        spyParams.updateCornerRadius(display2Context)
        spyParams.setProgress(
            /* fullscreenProgress= */ 1.0f,
            /* parentScale= */ 1.0f,
            /* taskViewScale= */ 1.0f,
        )
        assertThat(spyParams.currentDrawnCornerRadius).isEqualTo(display2WindowRadius)
    }
}
