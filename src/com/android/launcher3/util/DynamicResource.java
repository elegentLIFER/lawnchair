
package com.android.launcher3.util;

import android.content.Context;

import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.FractionRes;
import androidx.annotation.IntegerRes;

import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.ResourceProvider;

/**
 * Utility class to support customizing resource values using plugins
 *
 * To load resources, call
 *    DynamicResource.provider(context).getInt(resId) or any other supported methods
 *
 * To allow customization for a particular resource, add them to dynamic_resources.xml
 */
public class DynamicResource implements
        ResourceProvider, PluginListener<ResourceProvider>, SafeCloseable {

    private static final MainThreadInitializedObject<DynamicResource> INSTANCE =
            new MainThreadInitializedObject<>(DynamicResource::new);

    private final Context mContext;
    private ResourceProvider mPlugin;

    private DynamicResource(Context context) {
        mContext = context;
        PluginManagerWrapper.INSTANCE.get(context).addPluginListener(this,
                ResourceProvider.class, false /* allowedMultiple */);
    }

    @Override
    public void close() {
        PluginManagerWrapper.INSTANCE.get(mContext).removePluginListener(this);
    }

    @Override
    public int getInt(@IntegerRes int resId) {
        return mContext.getResources().getInteger(resId);
    }

    @Override
    public float getFraction(@FractionRes int resId) {
        return mContext.getResources().getFraction(resId, 1, 1);
    }

    @Override
    public float getDimension(@DimenRes int resId) {
        return mContext.getResources().getDimension(resId);
    }

    @Override
    public int getColor(@ColorRes int resId) {
        return mContext.getResources().getColor(resId, null);
    }

    @Override
    public float getFloat(@DimenRes int resId) {
        return mContext.getResources().getFloat(resId);
    }

    @Override
    public void onPluginConnected(ResourceProvider plugin, Context context) {
        mPlugin = plugin;
    }

    @Override
    public void onPluginDisconnected(ResourceProvider plugin) {
        mPlugin = null;
    }

    /**
     * Returns the currently active or default provider
     */
    public static ResourceProvider provider(Context context) {
        DynamicResource dr = DynamicResource.INSTANCE.get(context);
        ResourceProvider plugin = dr.mPlugin;
        return plugin == null ? dr : plugin;
    }
}
