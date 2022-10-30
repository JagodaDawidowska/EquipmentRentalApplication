package com.jdawidowska.equipmentrentalservice.api;

public enum ApiEndpoints {

    USER_HISTORY("history/user/DTO"),
    USER_RENT("renting/rent"),
    USER_INVENTORY("inventory");

    private final String path;

    ApiEndpoints(String path) {
        this.path = path;
    }

    public String getPath() {
        return ApiConfig.getUrl() + path;
    }
}
