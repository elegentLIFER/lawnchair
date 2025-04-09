

package com.android.systemui.plugins;

import android.animation.Animator;
import android.annotation.NonNull;
import android.view.View;

import com.android.systemui.plugins.annotations.ProvidesInterface;

/**
 * Customize toasts displayed by SystemUI (via Toast#makeText)
 */
@ProvidesInterface(action = ToastPlugin.ACTION, version = ToastPlugin.VERSION)
public interface ToastPlugin extends Plugin {

    String ACTION = "com.android.systemui.action.PLUGIN_TOAST";
    int VERSION = 1;

    /**
     * Creates a CustomPluginToast.
     */
    @NonNull Toast createToast(CharSequence text, String packageName, int userId);

    /**
     * Custom Toast with the ability to change toast positioning, styling and animations.
     */
    interface Toast {
        /**
         * Retrieve the Toast view's gravity.
         * If no changes, returns null.
         */
        default Integer getGravity() {
            return null;
        }

        /**
         * Retrieve the Toast view's X-offset.
         * If no changes, returns null.
         */
        default Integer getXOffset() {
            return null;
        }

        /**
         * Retrieve the Toast view's Y-offset.
         * If no changes, returns null.
         */
        default Integer getYOffset() {
            return null;
        }

        /**
         * Retrieve the Toast view's horizontal margin.
         * If no changes, returns null.
         */
        default Integer getHorizontalMargin()  {
            return null;
        }

        /**
         * Retrieve the Toast view's vertical margin.
         * If no changes, returns null.
         */
        default Integer getVerticalMargin()  {
            return null;
        }

        /**
         * Retrieve the Toast view to show.
         * If no changes, returns null.
         */
        default View getView() {
            return null;
        }

        /**
         * Retrieve the Toast's animate in.
         * If no changes, returns null.
         */
        default Animator getInAnimation() {
            return null;
        }

        /**
         * Retrieve the Toast's animate out.
         * If no changes, returns null.
         */
        default Animator getOutAnimation() {
            return null;
        }

        /**
         * Called on orientation changes.
         */
        default void onOrientationChange(int orientation) {  }
    }
}
