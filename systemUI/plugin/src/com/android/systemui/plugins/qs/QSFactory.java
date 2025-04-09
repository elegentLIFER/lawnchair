

package com.android.systemui.plugins.qs;

import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;

/**
 * Plugin that has the ability to create or override any part of
 * QS tiles.
 */
@ProvidesInterface(action = QSFactory.ACTION, version = QSFactory.VERSION)
@DependsOn(target = QSTile.class)
@DependsOn(target = QSTileView.class)
public interface QSFactory extends Plugin {

    String ACTION = "com.android.systemui.action.PLUGIN_QS_FACTORY";
    int VERSION = 3;

    QSTile createTile(String tileSpec);
}
