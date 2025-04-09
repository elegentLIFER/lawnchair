

package com.android.launcher3.responsive

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.android.launcher3.AbstractDeviceProfileTest
import com.android.launcher3.responsive.ResponsiveSpec.DimensionType
import com.android.launcher3.util.TestResourceHelper
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@SmallTest
@RunWith(AndroidJUnit4::class)
class CalculatedHotseatSpecTest : AbstractDeviceProfileTest() {
    override val runningContext: Context = InstrumentationRegistry.getInstrumentation().context
    val deviceSpec = deviceSpecs["phone"]!!
    val aspectRatio = deviceSpec.naturalSize.first.toFloat() / deviceSpec.naturalSize.second

    /**
     * This test tests:
     * - (height spec) gets the correct breakpoint from the XML - skips the first breakpoint
     */
    @Test
    fun normalPhone_returnsSecondBreakpointSpec() {
        initializeVarsForPhone(deviceSpec)

        // Hotseat uses the whole device height
        val availableHeight = deviceSpec.naturalSize.second

        val hotseatSpecsProvider =
            HotseatSpecsProvider.create(TestResourceHelper(context, "valid_hotseat_file".xmlToId()))
        val heightSpec =
            hotseatSpecsProvider.getCalculatedSpec(
                aspectRatio,
                DimensionType.HEIGHT,
                availableHeight
            )

        assertThat(heightSpec.availableSpace).isEqualTo(availableHeight)
        assertThat(heightSpec.hotseatQsbSpace).isEqualTo(95)
        assertThat(heightSpec.edgePadding).isEqualTo(126)
    }

    /**
     * This test tests:
     * - (height spec) gets the correct breakpoint from the XML - use the first breakpoint
     */
    @Test
    fun smallPhone_returnsFirstBreakpointSpec() {
        deviceSpec.densityDpi = 540 // larger display size
        initializeVarsForPhone(deviceSpec)

        // Hotseat uses the whole device height
        val availableHeight = deviceSpec.naturalSize.second

        val hotseatSpecsProvider =
            HotseatSpecsProvider.create(TestResourceHelper(context, "valid_hotseat_file".xmlToId()))
        val heightSpec =
            hotseatSpecsProvider.getCalculatedSpec(
                aspectRatio,
                DimensionType.HEIGHT,
                availableHeight
            )

        assertThat(heightSpec.availableSpace).isEqualTo(availableHeight)
        assertThat(heightSpec.hotseatQsbSpace).isEqualTo(81)
        assertThat(heightSpec.edgePadding).isEqualTo(162)
    }

    /**
     * This test tests:
     * - (width spec) gets the correct breakpoint from the XML - skips the first breakpoint
     */
    @Test
    fun normalPhoneLandscape_returnsSecondBreakpointSpec() {
        initializeVarsForPhone(deviceSpec, isVerticalBar = true)

        // Hotseat uses the whole device width
        val availableWidth = deviceSpec.naturalSize.second

        val hotseatSpecsProvider =
            HotseatSpecsProvider.create(
                TestResourceHelper(context, "valid_hotseat_land_file".xmlToId())
            )
        val widthSpec =
            hotseatSpecsProvider.getCalculatedSpec(aspectRatio, DimensionType.WIDTH, availableWidth)

        assertThat(widthSpec.availableSpace).isEqualTo(availableWidth)
        assertThat(widthSpec.hotseatQsbSpace).isEqualTo(0)
        assertThat(widthSpec.edgePadding).isEqualTo(168)
    }
}
