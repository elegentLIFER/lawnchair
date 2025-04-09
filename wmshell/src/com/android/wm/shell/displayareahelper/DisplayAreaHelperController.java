

package com.android.wm.shell.displayareahelper;

import android.view.SurfaceControl;

import com.android.wm.shell.RootDisplayAreaOrganizer;

import java.util.concurrent.Executor;
import java.util.function.Consumer;

public class DisplayAreaHelperController implements DisplayAreaHelper {

    private final Executor mExecutor;
    private final RootDisplayAreaOrganizer mRootDisplayAreaOrganizer;

    public DisplayAreaHelperController(Executor executor,
            RootDisplayAreaOrganizer rootDisplayAreaOrganizer) {
        mExecutor = executor;
        mRootDisplayAreaOrganizer = rootDisplayAreaOrganizer;
    }

    @Override
    public void attachToRootDisplayArea(int displayId, SurfaceControl.Builder builder,
            Consumer<SurfaceControl.Builder> onUpdated) {
        mExecutor.execute(() -> {
            mRootDisplayAreaOrganizer.attachToDisplayArea(displayId, builder);
            onUpdated.accept(builder);
        });
    }
}
