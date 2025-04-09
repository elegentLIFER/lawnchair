

package com.android.launcher3.responsive

import android.content.res.TypedArray
import com.android.launcher3.R
import com.android.launcher3.responsive.ResponsiveSpec.Companion.ResponsiveSpecType
import com.android.launcher3.responsive.ResponsiveSpec.DimensionType

/**
 * Base class for responsive specs that holds a list of width and height specs.
 *
 * @param widthSpecs List of width responsive specifications
 * @param heightSpecs List of height responsive specifications
 */
class ResponsiveSpecGroup<T : IResponsiveSpec>(
    val aspectRatio: Float,
    widthSpecs: List<T>,
    heightSpecs: List<T>
) {
    val widthSpecs: List<T>
    val heightSpecs: List<T>

    init {
        check(aspectRatio > 0f) { "Invalid aspect ratio! Aspect ratio should be bigger than zero." }
        this.widthSpecs = widthSpecs.sortedBy { it.maxAvailableSize }
        this.heightSpecs = heightSpecs.sortedBy { it.maxAvailableSize }
    }

    /**
     * Get a [ResponsiveSpec] within the breakpoint.
     *
     * @param type Type of the spec to be retrieved (width or height)
     * @param availableSize The breakpoint for the spec
     * @return A [ResponsiveSpec].
     */
    fun getSpec(type: DimensionType, availableSize: Int): T {
        val spec =
            if (type == DimensionType.WIDTH) {
                widthSpecs.firstOrNull { availableSize <= it.maxAvailableSize }
            } else {
                heightSpecs.firstOrNull { availableSize <= it.maxAvailableSize }
            }
        check(spec != null) { "No available $type spec found within $availableSize. $this" }
        return spec
    }

    override fun toString(): String {
        fun printSpec(spec: IResponsiveSpec) =
            when (spec.specType) {
                ResponsiveSpecType.AllApps,
                ResponsiveSpecType.Folder,
                ResponsiveSpecType.Workspace -> (spec as ResponsiveSpec).toString()
                ResponsiveSpecType.Hotseat -> (spec as HotseatSpec).toString()
                ResponsiveSpecType.Cell -> (spec as CellSpec).toString()
            }

        val widthSpecsString = widthSpecs.joinToString(", ") { printSpec(it) }
        val heightSpecsString = heightSpecs.joinToString(", ") { printSpec(it) }
        return "ResponsiveSpecGroup(" +
            "aspectRatio=${aspectRatio}, " +
            "widthSpecs=[${widthSpecsString}], " +
            "heightSpecs=[${heightSpecsString}]" +
            ")"
    }

    companion object {
        const val XML_GROUP_NAME = "specs"

        fun <T : IResponsiveSpec> create(
            attrs: TypedArray,
            specs: List<T>
        ): ResponsiveSpecGroup<T> {
            val (widthSpecs, heightSpecs) =
                specs.partition { it.dimensionType == DimensionType.WIDTH }
            val aspectRatio = attrs.getFloat(R.styleable.ResponsiveSpecGroup_maxAspectRatio, 0f)
            return ResponsiveSpecGroup(aspectRatio, widthSpecs, heightSpecs)
        }
    }
}
