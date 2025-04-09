

package com.android.wm.shell.pip.tv;

import static android.app.Notification.Action.SEMANTIC_ACTION_DELETE;
import static android.app.Notification.Action.SEMANTIC_ACTION_NONE;

import android.annotation.NonNull;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteAction;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import com.android.internal.protolog.common.ProtoLog;
import com.android.wm.shell.common.TvWindowMenuActionButton;
import com.android.wm.shell.protolog.ShellProtoLogGroup;

import java.util.List;
import java.util.Objects;

/**
 * A TvPipAction for actions that the app provides via {@link
 * android.app.PictureInPictureParams.Builder#setCloseAction(RemoteAction)} or {@link
 * android.app.PictureInPictureParams.Builder#setActions(List)}.
 */
public class TvPipCustomAction extends TvPipAction {
    private static final String TAG = TvPipCustomAction.class.getSimpleName();

    private final RemoteAction mRemoteAction;

    TvPipCustomAction(@ActionType int actionType, @NonNull RemoteAction remoteAction,
            SystemActionsHandler systemActionsHandler) {
        super(actionType, systemActionsHandler);
        Objects.requireNonNull(remoteAction);
        mRemoteAction = remoteAction;
    }

    void populateButton(@NonNull TvWindowMenuActionButton button, Handler mainHandler) {
        if (button == null || mainHandler == null) return;
        if (mRemoteAction.getContentDescription().length() > 0) {
            button.setTextAndDescription(mRemoteAction.getContentDescription());
        } else {
            button.setTextAndDescription(mRemoteAction.getTitle());
        }
        button.setImageIconAsync(mRemoteAction.getIcon(), mainHandler);
        button.setEnabled(isCloseAction() || mRemoteAction.isEnabled());
        button.setIsCustomCloseAction(isCloseAction());
    }

    PendingIntent getPendingIntent() {
        return mRemoteAction.getActionIntent();
    }

    void executeAction() {
        super.executeAction();
        try {
            mRemoteAction.getActionIntent().send();
        } catch (PendingIntent.CanceledException e) {
            ProtoLog.w(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE,
                    "%s: Failed to send action, %s", TAG, e);
        }
    }

    @Override
    Notification.Action toNotificationAction(Context context) {
        Notification.Action.Builder builder = new Notification.Action.Builder(
                mRemoteAction.getIcon(),
                mRemoteAction.getTitle(),
                mRemoteAction.getActionIntent());
        Bundle extras = new Bundle();
        extras.putCharSequence(Notification.EXTRA_PICTURE_CONTENT_DESCRIPTION,
                mRemoteAction.getContentDescription());
        extras.putBoolean(TvPipAction.EXTRA_IS_PIP_CUSTOM_ACTION, true);
        builder.addExtras(extras);

        builder.setSemanticAction(isCloseAction()
                ? SEMANTIC_ACTION_DELETE : SEMANTIC_ACTION_NONE);
        builder.setContextual(true);
        return builder.build();
    }

}
