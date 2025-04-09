

package com.android.quickstep.taskbar.customization

import com.android.launcher3.taskbar.customization.TaskbarFeatureEvaluator
import com.android.launcher3.taskbar.customization.TaskbarIconSpecs
import com.android.launcher3.taskbar.customization.TaskbarSpecsEvaluator
import com.android.launcher3.util.LauncherMultivalentJUnit
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy
import org.mockito.kotlin.whenever

@RunWith(LauncherMultivalentJUnit::class)
class TaskbarSpecsEvaluatorTest {

    private val taskbarFeatureEvaluator = mock<TaskbarFeatureEvaluator>()
    private val taskbarSpecsEvaluator = spy(TaskbarSpecsEvaluator(taskbarFeatureEvaluator))

    @Test
    fun testGetIconSizeByGrid_whenTaskbarIsTransient_withValidRowAndColumn() {
        doReturn(true).whenever(taskbarFeatureEvaluator).isTransient
        assertThat(taskbarSpecsEvaluator.getIconSizeByGrid(6, 5))
            .isEqualTo(TaskbarIconSpecs.iconSize52dp)
    }

    @Test
    fun testGetIconSizeByGrid_whenTaskbarIsTransient_withInvalidRowAndColumn() {
        doReturn(true).whenever(taskbarFeatureEvaluator).isTransient
        assertThat(taskbarSpecsEvaluator.getIconSizeByGrid(1, 2))
            .isEqualTo(TaskbarIconSpecs.defaultTransientIconSize)
    }

    @Test
    fun testGetIconSizeByGrid_whenTaskbarIsPersistent() {
        doReturn(false).whenever(taskbarFeatureEvaluator).isTransient
        assertThat(taskbarSpecsEvaluator.getIconSizeByGrid(6, 5))
            .isEqualTo(TaskbarIconSpecs.defaultPersistentIconSize)
    }

    @Test
    fun testGetIconSizeStepDown_whenTaskbarIsPersistent() {
        doReturn(false).whenever(taskbarFeatureEvaluator).isTransient
        assertThat(taskbarSpecsEvaluator.getIconSizeStepDown(TaskbarIconSpecs.iconSize44dp))
            .isEqualTo(TaskbarIconSpecs.defaultPersistentIconSize)
    }

    @Test
    fun testGetIconSizeStepDown_whenTaskbarIsTransientAndIconSizeAreInBound() {
        doReturn(true).whenever(taskbarFeatureEvaluator).isTransient
        assertThat(taskbarSpecsEvaluator.getIconSizeStepDown(TaskbarIconSpecs.iconSize52dp))
            .isEqualTo(TaskbarIconSpecs.iconSize48dp)
    }

    @Test
    fun testGetIconSizeStepDown_whenTaskbarIsTransientAndIconSizeAreOutOfBound() {
        doReturn(true).whenever(taskbarFeatureEvaluator).isTransient
        assertThat(taskbarSpecsEvaluator.getIconSizeStepDown(TaskbarIconSpecs.iconSize44dp))
            .isEqualTo(TaskbarIconSpecs.iconSize44dp)
    }

    @Test
    fun testGetIconSizeStepUp_whenTaskbarIsPersistent() {
        doReturn(false).whenever(taskbarFeatureEvaluator).isTransient
        assertThat(taskbarSpecsEvaluator.getIconSizeStepUp(TaskbarIconSpecs.iconSize40dp))
            .isEqualTo(TaskbarIconSpecs.iconSize40dp)
    }

    @Test
    fun testGetIconSizeStepUp_whenTaskbarIsTransientAndIconSizeAreInBound() {
        doReturn(true).whenever(taskbarFeatureEvaluator).isTransient
        assertThat(taskbarSpecsEvaluator.getIconSizeStepUp(TaskbarIconSpecs.iconSize44dp))
            .isEqualTo(TaskbarIconSpecs.iconSize48dp)
    }

    @Test
    fun testGetIconSizeStepUp_whenTaskbarIsTransientAndIconSizeAreOutOfBound() {
        doReturn(true).whenever(taskbarFeatureEvaluator).isTransient
        assertThat(taskbarSpecsEvaluator.getIconSizeStepUp(TaskbarIconSpecs.iconSize52dp))
            .isEqualTo(TaskbarIconSpecs.iconSize52dp)
    }
}
