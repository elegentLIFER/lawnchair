
package com.android.wm.shell.common.bubbles

import android.testing.AndroidTestingRunner
import androidx.test.filters.SmallTest
import com.android.wm.shell.ShellTestCase
import com.android.wm.shell.common.bubbles.BubbleBarLocation.DEFAULT
import com.android.wm.shell.common.bubbles.BubbleBarLocation.LEFT
import com.android.wm.shell.common.bubbles.BubbleBarLocation.RIGHT
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@SmallTest
@RunWith(AndroidTestingRunner::class)
class BubbleBarLocationTest : ShellTestCase() {

    @Test
    fun isOnLeft_rtlEnabled_defaultsToLeft() {
        assertThat(DEFAULT.isOnLeft(isRtl = true)).isTrue()
    }

    @Test
    fun isOnLeft_rtlDisabled_defaultsToRight() {
        assertThat(DEFAULT.isOnLeft(isRtl = false)).isFalse()
    }

    @Test
    fun isOnLeft_left_trueForAllLanguageDirections() {
        assertThat(LEFT.isOnLeft(isRtl = false)).isTrue()
        assertThat(LEFT.isOnLeft(isRtl = true)).isTrue()
    }

    @Test
    fun isOnLeft_right_falseForAllLanguageDirections() {
        assertThat(RIGHT.isOnLeft(isRtl = false)).isFalse()
        assertThat(RIGHT.isOnLeft(isRtl = true)).isFalse()
    }
}
