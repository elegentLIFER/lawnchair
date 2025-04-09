
package com.android.launcher3.util

import android.content.Context
import android.content.res.Configuration
import android.util.DisplayMetrics
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@SmallTest
@RunWith(AndroidJUnit4::class)
class CellContentDimensionsTest {
    private var context: Context? = null
    private val runningContext: Context = ApplicationProvider.getApplicationContext()
    private lateinit var iconSizeSteps: IconSizeSteps

    @Before
    fun setup() {
        // 160dp makes 1px = 1dp
        val config =
            Configuration(runningContext.resources.configuration).apply {
                this.densityDpi = DisplayMetrics.DENSITY_DEFAULT
                fontScale = 1.0f
            }
        context = runningContext.createConfigurationContext(config)
        iconSizeSteps = IconSizeSteps(context!!.resources)
    }

    @Test
    fun dimensionsFitTheCell() {
        val cellSize = Pair(80, 104)
        val cellContentDimensions =
            CellContentDimensions(iconSizePx = 66, iconDrawablePaddingPx = 8, iconTextSizePx = 14)

        val contentHeight =
            cellContentDimensions.resizeToFitCellHeight(cellSize.second, iconSizeSteps)

        assertThat(contentHeight).isEqualTo(93)
        cellContentDimensions.run {
            assertThat(iconSizePx).isEqualTo(66)
            assertThat(iconDrawablePaddingPx).isEqualTo(8)
            assertThat(iconTextSizePx).isEqualTo(14)
        }
    }

    @Test
    fun decreasePadding() {
        val cellSize = Pair(67, 87)
        val cellContentDimensions =
            CellContentDimensions(iconSizePx = 66, iconDrawablePaddingPx = 8, iconTextSizePx = 14)

        val contentHeight =
            cellContentDimensions.resizeToFitCellHeight(cellSize.second, iconSizeSteps)

        assertThat(contentHeight).isEqualTo(87)
        cellContentDimensions.run {
            assertThat(iconSizePx).isEqualTo(66)
            assertThat(iconDrawablePaddingPx).isEqualTo(2)
            assertThat(iconTextSizePx).isEqualTo(14)
        }
    }

    @Test
    fun decreaseIcon() {
        val cellSize = Pair(65, 84)
        val cellContentDimensions =
            CellContentDimensions(iconSizePx = 66, iconDrawablePaddingPx = 8, iconTextSizePx = 14)

        val contentHeight =
            cellContentDimensions.resizeToFitCellHeight(cellSize.second, iconSizeSteps)

        assertThat(contentHeight).isEqualTo(82)
        cellContentDimensions.run {
            assertThat(iconSizePx).isEqualTo(63)
            assertThat(iconDrawablePaddingPx).isEqualTo(0)
            assertThat(iconTextSizePx).isEqualTo(14)
        }
    }

    @Test
    fun decreaseText() {
        val cellSize = Pair(63, 81)
        val cellContentDimensions =
            CellContentDimensions(iconSizePx = 66, iconDrawablePaddingPx = 8, iconTextSizePx = 14)

        val contentHeight =
            cellContentDimensions.resizeToFitCellHeight(cellSize.second, iconSizeSteps)

        assertThat(contentHeight).isEqualTo(81)
        cellContentDimensions.run {
            assertThat(iconSizePx).isEqualTo(63)
            assertThat(iconDrawablePaddingPx).isEqualTo(0)
            assertThat(iconTextSizePx).isEqualTo(13)
        }
    }

    @Test
    fun decreaseIconAndTextTwoSteps() {
        val cellSize = Pair(60, 78)
        val cellContentDimensions =
            CellContentDimensions(iconSizePx = 66, iconDrawablePaddingPx = 8, iconTextSizePx = 14)

        val contentHeight =
            cellContentDimensions.resizeToFitCellHeight(cellSize.second, iconSizeSteps)

        assertThat(contentHeight).isEqualTo(77)
        cellContentDimensions.run {
            assertThat(iconSizePx).isEqualTo(61)
            assertThat(iconDrawablePaddingPx).isEqualTo(0)
            assertThat(iconTextSizePx).isEqualTo(12)
        }
    }

    @Test
    fun decreaseIconAndTextToMinimum() {
        val cellSize = Pair(52, 63)
        val cellContentDimensions =
            CellContentDimensions(iconSizePx = 66, iconDrawablePaddingPx = 8, iconTextSizePx = 14)

        val contentHeight =
            cellContentDimensions.resizeToFitCellHeight(cellSize.second, iconSizeSteps)

        assertThat(contentHeight).isEqualTo(63)
        cellContentDimensions.run {
            assertThat(iconSizePx).isEqualTo(52)
            assertThat(iconDrawablePaddingPx).isEqualTo(0)
            assertThat(iconTextSizePx).isEqualTo(8)
        }
    }
}
