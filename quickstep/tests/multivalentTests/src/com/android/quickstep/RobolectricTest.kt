

package com.android.quickstep

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RobolectricTest {
    @Test
    fun test1() {
        val actual = 1 + 1
        assertThat(actual).isEqualTo(2)
    }
}
