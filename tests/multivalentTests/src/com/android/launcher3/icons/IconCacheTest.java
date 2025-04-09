
package com.android.launcher3.icons;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static com.android.launcher3.icons.IconCache.EXTRA_SHORTCUT_BADGE_OVERRIDE_PACKAGE;
import static com.android.launcher3.util.Executors.MODEL_EXECUTOR;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutInfo.Builder;
import android.os.PersistableBundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.android.launcher3.InvariantDeviceProfile;
import com.android.launcher3.model.data.AppInfo;
import com.android.launcher3.model.data.ItemInfoWithIcon;
import com.android.launcher3.model.data.PackageItemInfo;
import com.android.launcher3.settings.SettingsActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@SmallTest
@RunWith(AndroidJUnit4.class)
public class IconCacheTest {

    private Context mContext;
    private IconCache mIconCache;

    private ComponentName mMyComponent;

    @Before
    public void setup() {
        mContext = getInstrumentation().getTargetContext();
        mMyComponent = new ComponentName(mContext, SettingsActivity.class);

        // In memory icon cache
        mIconCache = new IconCache(mContext,
                InvariantDeviceProfile.INSTANCE.get(mContext), null,
                new LauncherIconProvider(mContext));
    }

    @Test
    public void getShortcutInfoBadge_nullComponent_overrideAllowed() throws Exception {
        String overridePackage = "com.android.settings";
        ItemInfoWithIcon item = getBadgingInfo(mContext, null, overridePackage);
        assertTrue(item instanceof PackageItemInfo);
        assertEquals(((PackageItemInfo) item).packageName, overridePackage);
    }

    @Test
    public void getShortcutInfoBadge_withComponent_overrideAllowed() throws Exception {
        String overridePackage = "com.android.settings";
        ItemInfoWithIcon item = getBadgingInfo(mContext, mMyComponent, overridePackage);
        assertTrue(item instanceof PackageItemInfo);
        assertEquals(((PackageItemInfo) item).packageName, overridePackage);
    }

    @Test
    public void getShortcutInfoBadge_nullComponent() throws Exception {
        ItemInfoWithIcon item = getBadgingInfo(mContext, null, null);
        assertTrue(item instanceof PackageItemInfo);
        assertEquals(((PackageItemInfo) item).packageName, mContext.getPackageName());
    }

    @Test
    public void getShortcutInfoBadge_withComponent() throws Exception {
        ItemInfoWithIcon item = getBadgingInfo(mContext, mMyComponent, null);
        assertTrue(item instanceof AppInfo);
        assertEquals(((AppInfo) item).componentName, mMyComponent);
    }

    @Test
    public void getShortcutInfoBadge_overrideNotAllowed() throws Exception {
        String overridePackage = "com.android.settings";
        String otherPackage = mContext.getPackageName() + ".does.not.exist";
        Context otherContext = new ContextWrapper(mContext) {
            @Override
            public String getPackageName() {
                return otherPackage;
            }
        };
        ItemInfoWithIcon item = getBadgingInfo(otherContext, null, overridePackage);
        assertTrue(item instanceof PackageItemInfo);
        // Badge is set to the original package, and not the override package
        assertEquals(((PackageItemInfo) item).packageName, otherPackage);
    }

    private ItemInfoWithIcon getBadgingInfo(Context context,
            @Nullable ComponentName cn, @Nullable String badgeOverride) throws Exception {
        Builder builder = new Builder(context, "test-shortcut")
                .setIntent(new Intent(Intent.ACTION_VIEW))
                .setShortLabel("Test");
        if (cn != null) {
            builder.setActivity(cn);
        }
        if (!TextUtils.isEmpty(badgeOverride)) {
            PersistableBundle extras = new PersistableBundle();
            extras.putString(EXTRA_SHORTCUT_BADGE_OVERRIDE_PACKAGE, badgeOverride);
            builder.setExtras(extras);
        }
        ShortcutInfo info = builder.build();
        return MODEL_EXECUTOR.submit(() -> mIconCache.getShortcutInfoBadgeItem(info)).get();
    }
}
