

package com.android.systemui.plugins;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;

public abstract class PluginFragment extends Fragment implements Plugin {

    private Context mPluginContext;

    @Override
    public void onCreate(Context sysuiContext, Context pluginContext) {
        mPluginContext = pluginContext;
    }

    @Override
    public LayoutInflater onGetLayoutInflater(Bundle savedInstanceState) {
        return super.onGetLayoutInflater(savedInstanceState).cloneInContext(getContext());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public Context getContext() {
        return mPluginContext;
    }
}
