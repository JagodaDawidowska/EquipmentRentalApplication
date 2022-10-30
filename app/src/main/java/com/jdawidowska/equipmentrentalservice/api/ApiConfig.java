package com.jdawidowska.equipmentrentalservice.api;

public class ApiConfig {

    private final static String prefix = "http://";
    private final static String hostname = "localhost";
    private final static String post = "8089";
    private final static String suffix = "/api/";

    private final static String ipJa = " ";
    private final static String ipMax = "192.168.0.34";

    public static String getUrl() {
        return prefix + ipMax + ":" + post + suffix;
    }
}
