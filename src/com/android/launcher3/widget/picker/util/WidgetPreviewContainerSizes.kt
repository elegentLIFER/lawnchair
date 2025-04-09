

package com.android.launcher3.widget.picker.util

/**
 * An ordered list of recommended sizes for the preview containers in handheld devices.
 *
 * Size of the preview container in which a widget's preview can be displayed.
 */
val HANDHELD_WIDGET_PREVIEW_SIZES: List<WidgetPreviewContainerSize> =
    listOf(
        WidgetPreviewContainerSize(spanX = 4, spanY = 3),
        WidgetPreviewContainerSize(spanX = 4, spanY = 2),
        WidgetPreviewContainerSize(spanX = 2, spanY = 3),
        WidgetPreviewContainerSize(spanX = 2, spanY = 2),
        WidgetPreviewContainerSize(spanX = 4, spanY = 1),
        WidgetPreviewContainerSize(spanX = 2, spanY = 1),
        WidgetPreviewContainerSize(spanX = 1, spanY = 1),
    )

/**
 * An ordered list of recommended sizes for the preview containers in tablet devices (with larger
 * grids).
 *
 * Size of the preview container in which a widget's preview can be displayed (by scaling the
 * preview if necessary).
 */
val TABLET_WIDGET_PREVIEW_SIZES: List<WidgetPreviewContainerSize> =
    listOf(
        WidgetPreviewContainerSize(spanX = 3, spanY = 4),
        WidgetPreviewContainerSize(spanX = 3, spanY = 3),
        WidgetPreviewContainerSize(spanX = 3, spanY = 2),
        WidgetPreviewContainerSize(spanX = 2, spanY = 3),
        WidgetPreviewContainerSize(spanX = 2, spanY = 2),
        WidgetPreviewContainerSize(spanX = 3, spanY = 1),
        WidgetPreviewContainerSize(spanX = 2, spanY = 1),
        WidgetPreviewContainerSize(spanX = 1, spanY = 1),
    )
