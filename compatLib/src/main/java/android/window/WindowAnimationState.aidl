

package android.window;

import android.graphics.PointF;
import android.graphics.RectF;

/**
 * Properties of a window animation at a given point in time.
 *
 * {@hide}
 */
parcelable WindowAnimationState {
    long timestamp;
    RectF bounds;
    float scale;
    float topLeftRadius;
    float topRightRadius;
    float bottomRightRadius;
    float bottomLeftRadius;
    PointF velocityPxPerMs;
}
