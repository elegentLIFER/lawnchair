
package com.android.launcher3.util

import android.content.Context
import android.content.res.Configuration
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@SmallTest
@RunWith(AndroidJUnit4::class)
class IconSizeStepsTest {
    private var context: Context? = null
    private val runningContext: Context = ApplicationProvider.getApplicationContext()
    private lateinit var iconSizeSteps: IconSizeSteps

    @Before
    fun setup() {
        // 160dp makes 1px = 1dp
        val config =
            Configuration(runningContext.resources.configuration).apply { this.densityDpi = 160 }
        context = runningContext.createConfigurationContext(config)
        iconSizeSteps = IconSizeSteps(context!!.resources)
    }

    @Test
    fun minimumIconSize() {
        assertThat(iconSizeSteps.minimumIconSize()).isEqualTo(52)
    }

    @Test
    fun getNextLowerIconSize() {
        assertThat(iconSizeSteps.getNextLowerIconSize(66)).isEqualTo(63)

        // Assert that never goes below minimum
        assertThat(iconSizeSteps.getNextLowerIconSize(52)).isEqualTo(52)
        assertThat(iconSizeSteps.getNextLowerIconSize(30)).isEqualTo(52)
    }

    @Test
    fun getIconSmallerThan() {
        assertThat(iconSizeSteps.getIconSmallerThan(60)).isEqualTo(59)

        // Assert that never goes below minimum
        assertThat(iconSizeSteps.getIconSmallerThan(52)).isEqualTo(52)
        assertThat(iconSizeSteps.getIconSmallerThan(30)).isEqualTo(52)
    }
}
