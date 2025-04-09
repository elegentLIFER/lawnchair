

package com.android.launcher3.dragndrop;

import android.annotation.TargetApi;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.pm.LauncherApps.PinItemRequest;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.launcher3.Launcher;
import com.android.launcher3.model.data.ItemInfo;
import com.android.launcher3.widget.WidgetAddFlowHandler;

/**
 * Extension of WidgetAddFlowHandler to handle pin item request behavior.
 *
 * No config activity is shown even if it is defined in widget config. And a callback is sent when
 * the widget is bound.
 */
@TargetApi(Build.VERSION_CODES.O)
public class PinWidgetFlowHandler extends WidgetAddFlowHandler implements Parcelable {

    private final PinItemRequest mRequest;

    public PinWidgetFlowHandler(AppWidgetProviderInfo providerInfo, PinItemRequest request) {
        super(providerInfo);
        mRequest = request;
    }

    protected PinWidgetFlowHandler(Parcel parcel) {
        super(parcel);
        mRequest = PinItemRequest.CREATOR.createFromParcel(parcel);
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        mRequest.writeToParcel(parcel, i);
    }

    @Override
    public boolean startConfigActivity(Launcher launcher, int appWidgetId, ItemInfo info,
            int requestCode) {
        Bundle extras = new Bundle();
        extras.putInt(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        mRequest.accept(extras);
        return false;
    }

    @Override
    public boolean needsConfigure() {
        return false;
    }

    public static final Parcelable.Creator<PinWidgetFlowHandler> CREATOR =
            new Parcelable.Creator<PinWidgetFlowHandler>() {
                public PinWidgetFlowHandler createFromParcel(Parcel source) {
                    return new PinWidgetFlowHandler(source);
                }

                public PinWidgetFlowHandler[] newArray(int size) {
                    return new PinWidgetFlowHandler[size];
                }
            };
}
