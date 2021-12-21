package com.bulltronics.rc.server.service;

import com.bulltronics.rc.server.util.Util;
import com.bulltronics.rc.server.model.Status;
import com.google.gson.Gson;

public class ConfigService {
    private static final Gson gson = new Gson();

    public static Status getServerName() {
        Status status = new Status();
        status.getData().addProperty("name", "Remote Control Server");
        return status;
    }

    public static Status getServerVersion() {
        Status status = new Status();
        status.getData().addProperty("version", "1.0");
        return status;
    }

    public static Status getServerHosts() {
        Status status = new Status();

        status.getData().add("hosts", gson.toJsonTree(Util.getHostAddresses()).getAsJsonArray());

        return status;
    }
}
