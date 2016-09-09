package com.ingeniartesoft.siiom;

import com.squareup.otto.Bus;

/**
 * Created by german on 9/08/16.
 */
public class BusProvider {

    public static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }

    public BusProvider() {}
}
