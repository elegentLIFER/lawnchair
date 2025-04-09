
package com.android.launcher3.util;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.ContextThemeWrapper;

import com.android.launcher3.DeviceProfile;
import com.android.launcher3.DeviceProfile.OnDeviceProfileChangeListener;
import com.android.launcher3.InvariantDeviceProfile;
import com.android.launcher3.views.ActivityContext;
import com.android.launcher3.views.BaseDragLayer;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link ContextWrapper} with internal Launcher interface for testing
 */
public class ActivityContextWrapper extends ContextThemeWrapper implements ActivityContext {

    private final List<OnDeviceProfileChangeListener> mDpChangeListeners = new ArrayList<>();

    private final DeviceProfile mProfile;
    private final MyDragLayer mMyDragLayer;

    public ActivityContextWrapper(Context base) {
        super(base, android.R.style.Theme_DeviceDefault);
        mProfile = InvariantDeviceProfile.INSTANCE.get(base).getDeviceProfile(base).copy(base);
        mMyDragLayer = new MyDragLayer(this);
    }

    @Override
    public BaseDragLayer getDragLayer() {
        return mMyDragLayer;
    }

    @Override
    public List<OnDeviceProfileChangeListener> getOnDeviceProfileChangeListeners() {
        return mDpChangeListeners;
    }

    @Override
    public DeviceProfile getDeviceProfile() {
        return mProfile;
    }

    private static class MyDragLayer extends BaseDragLayer<ActivityContextWrapper> {

        MyDragLayer(Context context) {
            super(context, null, 1);
        }

        @Override
        public void recreateControllers() {
            mControllers = new TouchController[0];
        }
    }
}
