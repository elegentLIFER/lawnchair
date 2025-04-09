
package com.android.launcher3.icons;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.UserHandle;

import androidx.annotation.NonNull;

import com.android.launcher3.icons.cache.CachingLogic;

public interface ComponentWithLabel {

    ComponentName getComponent();

    UserHandle getUser();

    CharSequence getLabel(PackageManager pm);


    class ComponentCachingLogic<T extends ComponentWithLabel> implements CachingLogic<T> {

        private final PackageManager mPackageManager;
        private final boolean mAddToMemCache;

        public ComponentCachingLogic(Context context, boolean addToMemCache) {
            mPackageManager = context.getPackageManager();
            mAddToMemCache = addToMemCache;
        }

        @Override
        @NonNull
        public ComponentName getComponent(@NonNull T object) {
            return object.getComponent();
        }

        @NonNull
        @Override
        public UserHandle getUser(@NonNull T object) {
            return object.getUser();
        }

        @NonNull
        @Override
        public CharSequence getLabel(@NonNull T object) {
            return object.getLabel(mPackageManager);
        }

        @NonNull
        @Override
        public BitmapInfo loadIcon(@NonNull Context context, @NonNull T object) {
            return BitmapInfo.LOW_RES_INFO;
        }

        @Override
        public boolean addToMemCache() {
            return mAddToMemCache;
        }
    }
}
