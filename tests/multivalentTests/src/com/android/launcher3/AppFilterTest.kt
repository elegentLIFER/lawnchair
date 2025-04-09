

package com.android.launcher3

import android.content.ComponentName
import android.content.Context
import android.content.res.Resources
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@SmallTest
@RunWith(AndroidJUnit4::class)
class AppFilterTest {

    @Mock private lateinit var mockContext: Context

    @Mock // Mock the Resources object as well
    private lateinit var mockResources: Resources

    private lateinit var appFilter: AppFilter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        `when`(mockContext.resources).thenReturn(mockResources) // Link the context and resources
        `when`(mockResources.getStringArray(R.array.filtered_components))
            .thenReturn(arrayOf("com.example.app1/Activity1"))
        appFilter = AppFilter(mockContext)
    }

    @Test
    fun shouldShowApp_notFiltered_returnsTrue() {
        val appToShow = ComponentName("com.example.app2", "Activity2")
        assertThat(appFilter.shouldShowApp(appToShow)).isTrue()
    }

    @Test
    fun shouldShowApp_filtered_returnsFalse() {
        val appToHide = ComponentName("com.example.app1", "Activity1")
        assertThat(appFilter.shouldShowApp(appToHide)).isFalse()
    }
}
