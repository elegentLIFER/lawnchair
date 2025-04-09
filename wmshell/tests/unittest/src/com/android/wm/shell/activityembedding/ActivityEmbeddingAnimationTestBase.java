

package com.android.wm.shell.activityembedding;

import static android.window.TransitionInfo.FLAG_FILLS_TASK;
import static android.window.TransitionInfo.FLAG_IN_TASK_WITH_EMBEDDED_ACTIVITY;

import static com.android.dx.mockito.inline.extended.ExtendedMockito.spyOn;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assume.assumeTrue;
import static org.mockito.Mockito.mock;

import android.animation.Animator;
import android.annotation.CallSuper;
import android.annotation.NonNull;
import android.graphics.Rect;
import android.os.IBinder;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import android.window.WindowContainerToken;

import com.android.wm.shell.ShellTestCase;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/** TestBase for ActivityEmbedding animation. */
abstract class ActivityEmbeddingAnimationTestBase extends ShellTestCase {

    @Mock
    ShellInit mShellInit;

    @Mock
    Transitions mTransitions;
    @Mock
    IBinder mTransition;
    @Mock
    SurfaceControl.Transaction mStartTransaction;
    @Mock
    SurfaceControl.Transaction mFinishTransaction;
    @Mock
    Animator mAnimator;

    ActivityEmbeddingController mController;
    ActivityEmbeddingAnimationRunner mAnimRunner;
    ActivityEmbeddingAnimationSpec mAnimSpec;
    Transitions.TransitionFinishCallback mFinishCallback;

    @CallSuper
    @Before
    public void setUp() {
        assumeTrue(Transitions.ENABLE_SHELL_TRANSITIONS);
        MockitoAnnotations.initMocks(this);
        mController = ActivityEmbeddingController.create(mContext, mShellInit, mTransitions);
        assertNotNull(mController);
        mAnimRunner = mController.mAnimationRunner;
        assertNotNull(mAnimRunner);
        mAnimSpec = mAnimRunner.mAnimationSpec;
        assertNotNull(mAnimSpec);
        mFinishCallback = (wct) -> {};
        spyOn(mController);
        spyOn(mAnimRunner);
        spyOn(mAnimSpec);
        spyOn(mFinishCallback);
    }

    /** Creates a mock {@link TransitionInfo.Change}. */
    static TransitionInfo.Change createChange(@TransitionInfo.ChangeFlags int flags) {
        TransitionInfo.Change c = new TransitionInfo.Change(mock(WindowContainerToken.class),
                mock(SurfaceControl.class));
        c.setFlags(flags);
        return c;
    }

    /**
     * Creates a mock {@link TransitionInfo.Change} with
     * {@link TransitionInfo#FLAG_IN_TASK_WITH_EMBEDDED_ACTIVITY} flag.
     */
    static TransitionInfo.Change createEmbeddedChange(@NonNull Rect startBounds,
            @NonNull Rect endBounds, @NonNull Rect taskBounds) {
        final TransitionInfo.Change change = createChange(FLAG_IN_TASK_WITH_EMBEDDED_ACTIVITY);
        change.setStartAbsBounds(startBounds);
        change.setEndAbsBounds(endBounds);
        if (taskBounds.width() == startBounds.width()
                && taskBounds.height() == startBounds.height()
                && taskBounds.width() == endBounds.width()
                && taskBounds.height() == endBounds.height()) {
            change.setFlags(FLAG_FILLS_TASK);
        }
        return change;
    }
}
