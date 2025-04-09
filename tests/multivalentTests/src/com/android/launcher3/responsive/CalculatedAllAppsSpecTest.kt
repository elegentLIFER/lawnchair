

package com.android.launcher3.responsive

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.android.launcher3.AbstractDeviceProfileTest
import com.android.launcher3.responsive.ResponsiveSpec.Companion.ResponsiveSpecType
import com.android.launcher3.responsive.ResponsiveSpec.DimensionType
import com.android.launcher3.util.TestResourceHelper
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@SmallTest
@RunWith(AndroidJUnit4::class)
class CalculatedAllAppsSpecTest : AbstractDeviceProfileTest() {
    override val runningContext: Context = InstrumentationRegistry.getInstrumentation().context

    /**
     * This test tests:
     * - (height spec) copy values from workspace
     * - (width spec) copy values from workspace
     */
    @Test
    fun normalPhone_copiesFromWorkspace() {
        val deviceSpec = deviceSpecs["phone"]!!
        val aspectRatio = deviceSpec.naturalSize.first.toFloat() / deviceSpec.naturalSize.second
        initializeVarsForPhone(deviceSpec)

        val availableWidth = deviceSpec.naturalSize.first
        // Hotseat size is roughly 495px on a real device,
        // it doesn't need to be precise on unit tests
        val availableHeight = deviceSpec.naturalSize.second - deviceSpec.statusBarNaturalPx - 495

        val workspaceSpecs =
            ResponsiveSpecsProvider.create(
                TestResourceHelper(context, "valid_workspace_file".xmlToId()),
                ResponsiveSpecType.Workspace
            )
        val widthSpec =
            workspaceSpecs.getCalculatedSpec(aspectRatio, DimensionType.WIDTH, 4, availableWidth)
        val heightSpec =
            workspaceSpecs.getCalculatedSpec(aspectRatio, DimensionType.HEIGHT, 5, availableHeight)

        val allAppsSpecs =
            ResponsiveSpecsProvider.create(
                TestResourceHelper(context, "valid_all_apps_file".xmlToId()),
                ResponsiveSpecType.AllApps
            )

        with(
            allAppsSpecs.getCalculatedSpec(
                aspectRatio,
                DimensionType.WIDTH,
                4,
                availableWidth,
                widthSpec
            )
        ) {
            assertThat(availableSpace).isEqualTo(availableWidth)
            assertThat(cells).isEqualTo(4)
            assertThat(startPaddingPx).isEqualTo(widthSpec.startPaddingPx)
            assertThat(endPaddingPx).isEqualTo(widthSpec.endPaddingPx)
            assertThat(gutterPx).isEqualTo(widthSpec.gutterPx)
            assertThat(cellSizePx).isEqualTo(widthSpec.cellSizePx)
        }

        with(
            allAppsSpecs.getCalculatedSpec(
                aspectRatio,
                DimensionType.HEIGHT,
                5,
                availableHeight,
                heightSpec
            )
        ) {
            assertThat(availableSpace).isEqualTo(availableHeight)
            assertThat(cells).isEqualTo(5)
            assertThat(startPaddingPx).isEqualTo(0)
            assertThat(endPaddingPx).isEqualTo(0)
            assertThat(gutterPx).isEqualTo(heightSpec.gutterPx)
            assertThat(cellSizePx).isEqualTo(heightSpec.cellSizePx)
        }
    }
}
