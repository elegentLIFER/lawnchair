

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
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@SmallTest
@RunWith(AndroidJUnit4::class)
class CalculatedFolderSpecTest : AbstractDeviceProfileTest() {
    override val runningContext: Context = InstrumentationRegistry.getInstrumentation().context
    private val deviceSpec = deviceSpecs["phone"]!!
    private val aspectRatio = deviceSpec.naturalSize.first.toFloat() / deviceSpec.naturalSize.second

    @Before
    fun setup() {
        initializeVarsForPhone(deviceSpec)
    }

    @Test
    fun validate_matchWidthWorkspace() {
        val columns = 6

        // Loading workspace specs
        val resourceHelperWorkspace = TestResourceHelper(context, "valid_workspace_file".xmlToId())
        val workspaceSpecs =
            ResponsiveSpecsProvider.create(resourceHelperWorkspace, ResponsiveSpecType.Workspace)

        // Loading folders specs
        val resourceHelperFolder = TestResourceHelper(context, "valid_folders_specs".xmlToId())
        val folderSpecs =
            ResponsiveSpecsProvider.create(resourceHelperFolder, ResponsiveSpecType.Folder)
        val specs = folderSpecs.getSpecsByAspectRatio(aspectRatio)

        assertThat(specs.widthSpecs.size).isEqualTo(2)
        assertThat(specs.widthSpecs[0].cellSize.matchWorkspace).isEqualTo(true)
        assertThat(specs.widthSpecs[1].cellSize.matchWorkspace).isEqualTo(false)

        // Validate width spec <= 800
        var availableWidth = deviceSpec.naturalSize.first
        var calculatedWorkspace =
            workspaceSpecs.getCalculatedSpec(
                aspectRatio,
                DimensionType.WIDTH,
                columns,
                availableWidth
            )
        var calculatedWidthFolderSpec =
            folderSpecs.getCalculatedSpec(
                aspectRatio,
                DimensionType.WIDTH,
                columns,
                availableWidth,
                calculatedWorkspace
            )
        with(calculatedWidthFolderSpec) {
            assertThat(availableSpace).isEqualTo(availableWidth)
            assertThat(cells).isEqualTo(columns)
            assertThat(startPaddingPx).isEqualTo(16.dpToPx())
            assertThat(endPaddingPx).isEqualTo(16.dpToPx())
            assertThat(gutterPx).isEqualTo(16.dpToPx())
            assertThat(cellSizePx).isEqualTo(calculatedWorkspace.cellSizePx)
        }

        // Validate width spec > 800
        availableWidth = 2000.dpToPx()
        calculatedWorkspace =
            workspaceSpecs.getCalculatedSpec(
                aspectRatio,
                DimensionType.WIDTH,
                columns,
                availableWidth
            )
        calculatedWidthFolderSpec =
            folderSpecs.getCalculatedSpec(
                aspectRatio,
                DimensionType.WIDTH,
                columns,
                availableWidth,
                calculatedWorkspace
            )
        with(calculatedWidthFolderSpec) {
            assertThat(availableSpace).isEqualTo(availableWidth)
            assertThat(cells).isEqualTo(columns)
            assertThat(startPaddingPx).isEqualTo(16.dpToPx())
            assertThat(endPaddingPx).isEqualTo(16.dpToPx())
            assertThat(gutterPx).isEqualTo(16.dpToPx())
            assertThat(cellSizePx).isEqualTo(102.dpToPx())
        }
    }

    @Test
    fun validate_matchHeightWorkspace() {
        // Hotseat is roughly 495px on a real device, it doesn't need to be precise on unit tests
        val hotseatSize = 495
        val statusBarHeight = deviceSpec.statusBarNaturalPx
        val availableHeight = deviceSpec.naturalSize.second - statusBarHeight - hotseatSize
        val rows = 5

        // Loading workspace specs
        val resourceHelperWorkspace = TestResourceHelper(context, "valid_workspace_file".xmlToId())
        val workspaceSpecs =
            ResponsiveSpecsProvider.create(resourceHelperWorkspace, ResponsiveSpecType.Workspace)

        // Loading folders specs
        val resourceHelperFolder = TestResourceHelper(context, "valid_folders_specs".xmlToId())
        val folderSpecs =
            ResponsiveSpecsProvider.create(resourceHelperFolder, ResponsiveSpecType.Folder)
        val specs = folderSpecs.getSpecsByAspectRatio(aspectRatio)

        assertThat(specs.heightSpecs.size).isEqualTo(1)
        assertThat(specs.heightSpecs[0].cellSize.matchWorkspace).isEqualTo(true)

        // Validate height spec
        val calculatedWorkspace =
            workspaceSpecs.getCalculatedSpec(
                aspectRatio,
                DimensionType.HEIGHT,
                rows,
                availableHeight
            )
        val calculatedFolderSpec =
            folderSpecs.getCalculatedSpec(
                aspectRatio,
                DimensionType.HEIGHT,
                rows,
                availableHeight,
                calculatedWorkspace
            )
        with(calculatedFolderSpec) {
            assertThat(availableSpace).isEqualTo(availableHeight)
            assertThat(cells).isEqualTo(rows)
            assertThat(startPaddingPx).isEqualTo(24.dpToPx())
            assertThat(endPaddingPx).isEqualTo(64.dpToPx())
            assertThat(gutterPx).isEqualTo(16.dpToPx())
            assertThat(cellSizePx).isEqualTo(calculatedWorkspace.cellSizePx)
        }
    }
}
