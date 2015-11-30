package org.succlz123.blueboard.config;

import com.squareup.otto.Bus;

/**
 * Created by succlz123 on 15/10/27.
 */
public final class BusProvider {
    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {
        // No instances.
    }
}