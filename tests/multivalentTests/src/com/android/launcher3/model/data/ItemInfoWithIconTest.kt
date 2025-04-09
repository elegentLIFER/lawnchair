

package com.android.launcher3.model.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.android.launcher3.pm.PackageInstallInfo
import com.android.launcher3.util.LauncherModelHelper
import com.google.common.truth.Truth
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@SmallTest
@RunWith(AndroidJUnit4::class)
class ItemInfoWithIconTest {

    private var context = LauncherModelHelper.SandboxModelContext()
    private lateinit var itemInfoWithIcon: ItemInfoWithIcon

    @Before
    fun setup() {
        itemInfoWithIcon =
            object : ItemInfoWithIcon() {
                override fun clone(): ItemInfoWithIcon? {
                    return null
                }
            }
    }

    @After
    fun tearDown() {
        context.destroy()
    }

    @Test
    fun itemInfoWithIconDefaultParamsTest() {
        Truth.assertThat(itemInfoWithIcon.isDisabled).isFalse()
        Truth.assertThat(itemInfoWithIcon.isPendingDownload).isFalse()
        Truth.assertThat(itemInfoWithIcon.isArchived).isFalse()
    }

    @Test
    fun isDisabledOrPendingTest() {
        itemInfoWithIcon.setProgressLevel(0, PackageInstallInfo.STATUS_INSTALLING)
        Truth.assertThat(itemInfoWithIcon.isDisabled).isFalse()
        Truth.assertThat(itemInfoWithIcon.isPendingDownload).isTrue()

        itemInfoWithIcon.setProgressLevel(1, PackageInstallInfo.STATUS_INSTALLING)
        Truth.assertThat(itemInfoWithIcon.isDisabled).isFalse()
        Truth.assertThat(itemInfoWithIcon.isPendingDownload).isFalse()
    }
}
