

package com.android.wm.shell.common;

import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.drawable.shapes.PathShape;

import androidx.annotation.NonNull;

/**
 * Wrapper around {@link PathShape}
 * that creates a shape with a triangular path (pointing up or down).
 *
 * This is the copy from SystemUI/recents.
 */
public class TriangleShape extends PathShape {
    private Path mTriangularPath;

    public TriangleShape(Path path, float stdWidth, float stdHeight) {
        super(path, stdWidth, stdHeight);
        mTriangularPath = path;
    }

    public static TriangleShape create(float width, float height, boolean isPointingUp) {
        Path triangularPath = new Path();
        if (isPointingUp) {
            triangularPath.moveTo(0, height);
            triangularPath.lineTo(width, height);
            triangularPath.lineTo(width / 2, 0);
            triangularPath.close();
        } else {
            triangularPath.moveTo(0, 0);
            triangularPath.lineTo(width / 2, height);
            triangularPath.lineTo(width, 0);
            triangularPath.close();
        }
        return new TriangleShape(triangularPath, width, height);
    }

    /** Create an arrow TriangleShape that points to the left or the right */
    public static TriangleShape createHorizontal(
            float width, float height, boolean isPointingLeft) {
        Path triangularPath = new Path();
        if (isPointingLeft) {
            triangularPath.moveTo(0, height / 2);
            triangularPath.lineTo(width, height);
            triangularPath.lineTo(width, 0);
            triangularPath.close();
        } else {
            triangularPath.moveTo(0, height);
            triangularPath.lineTo(width, height / 2);
            triangularPath.lineTo(0, 0);
            triangularPath.close();
        }
        return new TriangleShape(triangularPath, width, height);
    }

    @Override
    public void getOutline(@NonNull Outline outline) {
        outline.setPath(mTriangularPath);
    }
}
