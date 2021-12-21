package com.bulltronics.rc.server.controller;

import com.bulltronics.rc.server.util.Util;
import com.bulltronics.rc.server.model.Status;

public class UtilityController {
    public Status waitForMillis(long duration) {
        Status status = new Status();

        Util.waitForMillis(duration);

        return status;
    }
}
