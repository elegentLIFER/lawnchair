

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
class AllAppsSpecsTest : AbstractDeviceProfileTest() {
    override val runningContext: Context = InstrumentationRegistry.getInstrumentation().context
    val deviceSpec = deviceSpecs["phone"]!!
    val aspectRatio = deviceSpec.naturalSize.first.toFloat() / deviceSpec.naturalSize.second

    @Before
    fun setup() {
        initializeVarsForPhone(deviceSpec)
    }

    @Test
    fun parseValidFile() {
        val allAppsSpecs =
            ResponsiveSpecsProvider.create(
                TestResourceHelper(context, "valid_all_apps_file".xmlToId()),
                ResponsiveSpecType.AllApps
            )

        val specs = allAppsSpecs.getSpecsByAspectRatio(aspectRatio)
        assertThat(specs.heightSpecs.size).isEqualTo(1)
        assertThat(specs.heightSpecs[0].toString())
            .isEqualTo(
                "ResponsiveSpec(" +
                    "maxAvailableSize=26247, " +
                    "dimensionType=HEIGHT, " +
                    "specType=AllApps, " +
                    "startPadding=SizeSpec(fixedSize=0.0, " +
                    "ofAvailableSpace=0.0, " +
                    "ofRemainderSpace=0.0, " +
                    "matchWorkspace=false, " +
                    "maxSize=2147483647), " +
                    "endPadding=SizeSpec(fixedSize=0.0, " +
                    "ofAvailableSpace=0.0, " +
                    "ofRemainderSpace=0.0, " +
                    "matchWorkspace=false, " +
                    "maxSize=2147483647), " +
                    "gutter=SizeSpec(fixedSize=0.0, " +
                    "ofAvailableSpace=0.0, " +
                    "ofRemainderSpace=0.0, " +
                    "matchWorkspace=true, " +
                    "maxSize=2147483647), " +
                    "cellSize=SizeSpec(fixedSize=0.0, " +
                    "ofAvailableSpace=0.0, " +
                    "ofRemainderSpace=0.0, " +
                    "matchWorkspace=true, " +
                    "maxSize=2147483647)" +
                    ")"
            )

        assertThat(specs.widthSpecs.size).isEqualTo(1)
        assertThat(specs.widthSpecs[0].toString())
            .isEqualTo(
                "ResponsiveSpec(" +
                    "maxAvailableSize=26247, " +
                    "dimensionType=WIDTH, " +
                    "specType=AllApps, " +
                    "startPadding=SizeSpec(fixedSize=0.0, " +
                    "ofAvailableSpace=0.0, " +
                    "ofRemainderSpace=0.0, " +
                    "matchWorkspace=true, " +
                    "maxSize=2147483647), " +
                    "endPadding=SizeSpec(fixedSize=0.0, " +
                    "ofAvailableSpace=0.0, " +
                    "ofRemainderSpace=0.0, " +
                    "matchWorkspace=true, " +
                    "maxSize=2147483647), " +
                    "gutter=SizeSpec(fixedSize=0.0, " +
                    "ofAvailableSpace=0.0, " +
                    "ofRemainderSpace=0.0, " +
                    "matchWorkspace=true, " +
                    "maxSize=2147483647), " +
                    "cellSize=SizeSpec(fixedSize=0.0, " +
                    "ofAvailableSpace=0.0, " +
                    "ofRemainderSpace=0.0, " +
                    "matchWorkspace=true, " +
                    "maxSize=2147483647)" +
                    ")"
            )
    }

    @Test(expected = IllegalStateException::class)
    fun parseInvalidFile_missingTag_throwsError() {
        ResponsiveSpecsProvider.create(
            TestResourceHelper(context, "invalid_all_apps_file_case_1".xmlToId()),
            ResponsiveSpecType.AllApps
        )
    }

    @Test(expected = IllegalStateException::class)
    fun parseInvalidFile_moreThanOneValuePerTag_throwsError() {
        ResponsiveSpecsProvider.create(
            TestResourceHelper(context, "invalid_all_apps_file_case_2".xmlToId()),
            ResponsiveSpecType.AllApps
        )
    }

    @Test(expected = IllegalStateException::class)
    fun parseInvalidFile_valueBiggerThan1_throwsError() {
        ResponsiveSpecsProvider.create(
            TestResourceHelper(context, "invalid_all_apps_file_case_3".xmlToId()),
            ResponsiveSpecType.AllApps
        )
    }
}
