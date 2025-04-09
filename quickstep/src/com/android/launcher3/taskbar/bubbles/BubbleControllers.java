
package com.android.launcher3.taskbar.bubbles;

import com.android.launcher3.taskbar.TaskbarControllers;
import com.android.launcher3.util.RunnableList;

/**
 * Hosts various bubble controllers to facilitate passing between one another.
 */
public class BubbleControllers {

    public final BubbleBarController bubbleBarController;
    public final BubbleBarViewController bubbleBarViewController;
    public final BubbleStashController bubbleStashController;
    public final BubbleStashedHandleViewController bubbleStashedHandleViewController;
    public final BubbleDragController bubbleDragController;
    public final BubbleDismissController bubbleDismissController;
    public final BubbleBarPinController bubbleBarPinController;
    public final BubblePinController bubblePinController;

    private final RunnableList mPostInitRunnables = new RunnableList();

    /**
     * Want to add a new controller? Don't forget to:
     * * Call init
     * * Call onDestroy
     */
    public BubbleControllers(
            BubbleBarController bubbleBarController,
            BubbleBarViewController bubbleBarViewController,
            BubbleStashController bubbleStashController,
            BubbleStashedHandleViewController bubbleStashedHandleViewController,
            BubbleDragController bubbleDragController,
            BubbleDismissController bubbleDismissController,
            BubbleBarPinController bubbleBarPinController,
            BubblePinController bubblePinController) {
        this.bubbleBarController = bubbleBarController;
        this.bubbleBarViewController = bubbleBarViewController;
        this.bubbleStashController = bubbleStashController;
        this.bubbleStashedHandleViewController = bubbleStashedHandleViewController;
        this.bubbleDragController = bubbleDragController;
        this.bubbleDismissController = bubbleDismissController;
        this.bubbleBarPinController = bubbleBarPinController;
        this.bubblePinController = bubblePinController;
    }

    /**
     * Initializes all controllers. Note that controllers can now reference each
     * other through this
     * BubbleControllers instance, but should be careful to only access things that
     * were created
     * in constructors for now, as some controllers may still be waiting for init().
     */
    public void init(TaskbarControllers taskbarControllers) {
        bubbleBarController.init(this,
                taskbarControllers.navbarButtonsViewController::isImeVisible);
        bubbleBarViewController.init(taskbarControllers, this);
        bubbleStashedHandleViewController.init(taskbarControllers, this);
        bubbleStashController.init(taskbarControllers, this);
        bubbleDragController.init(/* bubbleControllers = */ this);
        bubbleDismissController.init(/* bubbleControllers = */ this);
        bubbleBarPinController.init(this);
        bubblePinController.init(this);

        mPostInitRunnables.executeAllAndDestroy();
    }

    /**
     * If all controllers are already initialized, runs the given callback
     * immediately. Otherwise,
     * queues it to run after calling init() on all controllers. This should likely
     * be used in any
     * case where one controller is telling another controller to do something
     * inside init().
     */
    public void runAfterInit(Runnable runnable) {
        // If this has been executed in init, it automatically runs adds to it.
        mPostInitRunnables.add(runnable);
    }

    /**
     * Cleans up all controllers.
     */
    public void onDestroy() {
        bubbleStashedHandleViewController.onDestroy();
        bubbleBarController.onDestroy();
    }
}
