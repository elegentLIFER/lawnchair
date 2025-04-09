

package com.android.launcher3.responsive

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.android.launcher3.AbstractDeviceProfileTest
import com.android.launcher3.responsive.ResponsiveSpec.Companion.ResponsiveSpecType
import com.android.launcher3.util.TestResourceHelper
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@SmallTest
@RunWith(AndroidJUnit4::class)
class WorkspaceSpecsTest : AbstractDeviceProfileTest() {
    override val runningContext: Context = InstrumentationRegistry.getInstrumentation().context
    val deviceSpec = deviceSpecs["phone"]!!
    val aspectRatio = deviceSpec.naturalSize.first.toFloat() / deviceSpec.naturalSize.second

    @Before
    fun setup() {
        initializeVarsForPhone(deviceSpec)
    }

    @Test
    fun parseValidFile() {
        val workspaceSpecs =
            ResponsiveSpecsProvider.create(
                TestResourceHelper(context, "valid_workspace_file".xmlToId()),
                ResponsiveSpecType.Workspace
            )

        val specs = workspaceSpecs.getSpecsByAspectRatio(aspectRatio)
        assertThat(specs.heightSpecs.size).isEqualTo(3)
        assertThat(specs.heightSpecs[0].toString())
            .isEqualTo(
                "ResponsiveSpec(" +
                    "maxAvailableSize=1533, " +
                    "dimensionType=HEIGHT, " +
                    "specType=Workspace, " +
                    "startPadding=SizeSpec(fixedSize=0.0, " +
                    "ofAvailableSpace=0.0, " +
                    "ofRemainderSpace=0.0, " +
                    "matchWorkspace=false, " +
                    "maxSize=2147483647), " +
                    "endPadding=SizeSpec(fixedSize=84.0, " +
                    "ofAvailableSpace=0.0, " +
                    "ofRemainderSpace=0.0, " +
                    "matchWorkspace=false, " +
                    "maxSize=2147483647), " +
                    "gutter=SizeSpec(fixedSize=42.0, " +
                    "ofAvailableSpace=0.0, " +
                    "ofRemainderSpace=0.0, " +
                    "matchWorkspace=false, " +
                    "maxSize=2147483647), " +
                    "cellSize=SizeSpec(fixedSize=0.0, " +
                    "ofAvailableSpace=0.15808, " +
                    "ofRemainderSpace=0.0, " +
                    "matchWorkspace=false, " +
                    "maxSize=2147483647)" +
                    ")"
            )
        assertThat(specs.heightSpecs[1].toString())
            .isEqualTo(
                "ResponsiveSpec(" +
                    "maxAvailableSize=1607, " +
                    "dimensionType=HEIGHT, " +
                    "specType=Workspace, " +
                    "startPadding=SizeSpec(fixedSize=0.0, " +
                    "ofAvailableSpace=0.0, " +
                    "ofRemainderSpace=0.0, " +
                    "matchWorkspace=false, " +
                    "maxSize=2147483647), " +
                    "endPadding=SizeSpec(fixedSize=0.0, " +
                    "ofAvailableSpace=0.0, " +
                    "ofRemainderSpace=1.0, " +
                    "matchWorkspace=false, " +
                    "maxSize=2147483647), " +
                    "gutter=SizeSpec(fixedSize=42.0, " +
                    "ofAvailableSpace=0.0, " +
                    "ofRemainderSpace=0.0, " +
                    "matchWorkspace=false, " +
                    "maxSize=2147483647), " +
                    "cellSize=SizeSpec(fixedSize=273.0, " +
                    "ofAvailableSpace=0.0, " +
                    "ofRemainderSpace=0.0, " +
                    "matchWorkspace=false, " +
                    "maxSize=2147483647)" +
                    ")"
            )
        assertThat(specs.heightSpecs[2].toString())
            .isEqualTo(
                "ResponsiveSpec(" +
                    "maxAvailableSize=26247, " +
                    "dimensionType=HEIGHT, " +
                    "specType=Workspace, " +
                    "startPadding=SizeSpec(fixedSize=21.0, " +
                    "ofAvailableSpace=0.0, " +
                    "ofRemainderSpace=0.0, " +
                    "matchWorkspace=false, " +
                    "maxSize=2147483647), " +
                    "endPadding=SizeSpec(fixedSize=0.0, " +
                    "ofAvailableSpace=0.0, " +
                    "ofRemainderSpace=1.0, " +
                    "matchWorkspace=false, " +
                    "maxSize=2147483647), " +
                    "gutter=SizeSpec(fixedSize=42.0, " +
                    "ofAvailableSpace=0.0, " +
                    "ofRemainderSpace=0.0, " +
                    "matchWorkspace=false, " +
                    "maxSize=2147483647), " +
                    "cellSize=SizeSpec(fixedSize=273.0, " +
                    "ofAvailableSpace=0.0, " +
                    "ofRemainderSpace=0.0, " +
                    "matchWorkspace=false, " +
                    "maxSize=2147483647)" +
                    ")"
            )
        assertThat(specs.widthSpecs.size).isEqualTo(1)
        assertThat(specs.widthSpecs[0].toString())
            .isEqualTo(
                "ResponsiveSpec(" +
                    "maxAvailableSize=26247, " +
                    "dimensionType=WIDTH, " +
                    "specType=Workspace, " +
                    "startPadding=SizeSpec(fixedSize=58.0, " +
                    "ofAvailableSpace=0.0, " +
                    "ofRemainderSpace=0.0, " +
                    "matchWorkspace=false, " +
                    "maxSize=2147483647), " +
                    "endPadding=SizeSpec(fixedSize=58.0, " +
                    "ofAvailableSpace=0.0, " +
                    "ofRemainderSpace=0.0, " +
                    "matchWorkspace=false, " +
                    "maxSize=2147483647), " +
                    "gutter=SizeSpec(fixedSize=42.0, " +
                    "ofAvailableSpace=0.0, " +
                    "ofRemainderSpace=0.0, " +
                    "matchWorkspace=false, " +
                    "maxSize=2147483647), " +
                    "cellSize=SizeSpec(fixedSize=0.0, " +
                    "ofAvailableSpace=0.0, " +
                    "ofRemainderSpace=1.0, " +
                    "matchWorkspace=false, " +
                    "maxSize=2147483647)" +
                    ")"
            )
    }

    @Test(expected = IllegalStateException::class)
    fun parseInvalidFile_missingTag_throwsError() {
        ResponsiveSpecsProvider.create(
            TestResourceHelper(context, "invalid_workspace_file_case_1".xmlToId()),
            ResponsiveSpecType.Workspace
        )
    }

    @Test(expected = IllegalStateException::class)
    fun parseInvalidFile_moreThanOneValuePerTag_throwsError() {
        ResponsiveSpecsProvider.create(
            TestResourceHelper(context, "invalid_workspace_file_case_2".xmlToId()),
            ResponsiveSpecType.Workspace
        )
    }

    @Test(expected = IllegalStateException::class)
    fun parseInvalidFile_valueBiggerThan1_throwsError() {
        ResponsiveSpecsProvider.create(
            TestResourceHelper(context, "invalid_workspace_file_case_3".xmlToId()),
            ResponsiveSpecType.Workspace
        )
    }

    @Test(expected = IllegalStateException::class)
    fun parseInvalidFile_matchWorkspace_true_throwsError() {
        ResponsiveSpecsProvider.create(
            TestResourceHelper(context, "invalid_workspace_file_case_4".xmlToId()),
            ResponsiveSpecType.Workspace
        )
    }
}
