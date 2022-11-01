package com.jdawidowska.equipmentrentalservice.api;

public class ApiConfig {

    private final static String prefix = "http://";
    private final static String host = "localhost";
    private final static String port = "8089";
    private final static String suffix = "/api/";

    private final static String ipJagu = "";
    private final static String ipMax = "192.168.0.33";

    static String getUrl() {
        return prefix + ipMax + ":" + port + suffix;
    }
}
