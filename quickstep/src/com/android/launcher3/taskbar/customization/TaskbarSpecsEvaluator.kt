

package com.android.launcher3.taskbar.customization

/** Evaluates the taskbar specs based on the taskbar grid size and the taskbar icon size. */
class TaskbarSpecsEvaluator(private val taskbarFeatureEvaluator: TaskbarFeatureEvaluator) {

    fun getIconSizeByGrid(row: Int, column: Int): TaskbarIconSize {
        return if (taskbarFeatureEvaluator.isTransient) {
            TaskbarIconSpecs.transientTaskbarIconSizeByGridSize.getOrDefault(
                Pair(row, column),
                TaskbarIconSpecs.defaultTransientIconSize,
            )
        } else {
            TaskbarIconSpecs.defaultPersistentIconSize
        }
    }

    fun getIconSizeStepDown(iconSize: TaskbarIconSize): TaskbarIconSize {
        if (!taskbarFeatureEvaluator.isTransient) return TaskbarIconSpecs.defaultPersistentIconSize

        val currentIconSizeIndex = TaskbarIconSpecs.transientTaskbarIconSizes.indexOf(iconSize)
        // return the current icon size if supplied icon size is unknown or we have reached the
        // min icon size.
        return if (currentIconSizeIndex == -1 || currentIconSizeIndex == 0) iconSize
        else TaskbarIconSpecs.transientTaskbarIconSizes[currentIconSizeIndex - 1]
    }

    fun getIconSizeStepUp(iconSize: TaskbarIconSize): TaskbarIconSize {
        if (!taskbarFeatureEvaluator.isTransient) return TaskbarIconSpecs.defaultPersistentIconSize

        val currentIconSizeIndex = TaskbarIconSpecs.transientTaskbarIconSizes.indexOf(iconSize)
        // return the current icon size if supplied icon size is unknown or we have reached the
        // max icon size.
        return if (
            currentIconSizeIndex == -1 ||
                currentIconSizeIndex == TaskbarIconSpecs.transientTaskbarIconSizes.size - 1
        ) {
            iconSize
        } else {
            TaskbarIconSpecs.transientTaskbarIconSizes.get(currentIconSizeIndex + 1)
        }
    }
}

data class TaskbarIconSize(val size: Int)
