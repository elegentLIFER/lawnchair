

package com.android.wm.shell.common.bubbles

import android.os.Parcel
import android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE
import android.testing.AndroidTestingRunner
import androidx.test.filters.SmallTest
import com.android.wm.shell.ShellTestCase
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@SmallTest
@RunWith(AndroidTestingRunner::class)
class BubbleInfoTest : ShellTestCase() {

    @Test
    fun bubbleInfo() {
        val bubbleInfo =
            BubbleInfo(
                "key",
                0,
                "shortcut id",
                null,
                6,
                "com.some.package",
                "title",
                "Some app",
                true
            )
        val parcel = Parcel.obtain()
        bubbleInfo.writeToParcel(parcel, PARCELABLE_WRITE_RETURN_VALUE)
        parcel.setDataPosition(0)

        val bubbleInfoFromParcel = BubbleInfo.CREATOR.createFromParcel(parcel)

        assertThat(bubbleInfo.key).isEqualTo(bubbleInfoFromParcel.key)
        assertThat(bubbleInfo.flags).isEqualTo(bubbleInfoFromParcel.flags)
        assertThat(bubbleInfo.shortcutId).isEqualTo(bubbleInfoFromParcel.shortcutId)
        assertThat(bubbleInfo.icon).isEqualTo(bubbleInfoFromParcel.icon)
        assertThat(bubbleInfo.userId).isEqualTo(bubbleInfoFromParcel.userId)
        assertThat(bubbleInfo.packageName).isEqualTo(bubbleInfoFromParcel.packageName)
        assertThat(bubbleInfo.title).isEqualTo(bubbleInfoFromParcel.title)
        assertThat(bubbleInfo.appName).isEqualTo(bubbleInfoFromParcel.appName)
        assertThat(bubbleInfo.isImportantConversation)
            .isEqualTo(bubbleInfoFromParcel.isImportantConversation)
    }
}
