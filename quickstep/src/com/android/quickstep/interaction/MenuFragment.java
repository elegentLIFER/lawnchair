
package com.android.quickstep.interaction;

import static com.android.quickstep.interaction.GestureSandboxActivity.KEY_GESTURE_COMPLETE;
import static com.android.quickstep.interaction.GestureSandboxActivity.KEY_TUTORIAL_TYPE;
import static com.android.quickstep.interaction.GestureSandboxActivity.KEY_USE_TUTORIAL_MENU;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.launcher3.R;

/** Displays the gesture nav tutorial menu. */
public final class MenuFragment extends GestureSandboxFragment {

    @NonNull
    @Override
    GestureSandboxFragment recreateFragment() {
        return new MenuFragment();
    }

    @Override
    boolean canRecreateFragment() {
        return true;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(
                R.layout.gesture_tutorial_step_menu, container, false);

        root.findViewById(R.id.gesture_tutorial_menu_home_button).setOnClickListener(
                v -> launchTutorialStep(TutorialController.TutorialType.HOME_NAVIGATION));
        root.findViewById(R.id.gesture_tutorial_menu_back_button).setOnClickListener(
                v -> launchTutorialStep(TutorialController.TutorialType.BACK_NAVIGATION));
        root.findViewById(R.id.gesture_tutorial_menu_overview_button).setOnClickListener(
                v -> launchTutorialStep(TutorialController.TutorialType.OVERVIEW_NAVIGATION));
        root.findViewById(R.id.gesture_tutorial_menu_done_button).setOnClickListener(
                v -> close());

        return root;
    }

    @Override
    boolean shouldDisableSystemGestures() {
        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(KEY_USE_TUTORIAL_MENU, true);
        savedInstanceState.remove(KEY_TUTORIAL_TYPE);
        savedInstanceState.remove(KEY_GESTURE_COMPLETE);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void launchTutorialStep(@NonNull TutorialController.TutorialType tutorialType) {
        ((GestureSandboxActivity) getActivity()).launchTutorialStep(tutorialType, true);
    }
}
