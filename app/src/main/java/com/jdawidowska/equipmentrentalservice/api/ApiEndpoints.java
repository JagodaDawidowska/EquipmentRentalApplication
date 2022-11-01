package com.jdawidowska.equipmentrentalservice.api;

public enum ApiEndpoints {

    RENT_ITEM("renting/rent"),
    RETURN_ITEM("renting/return"),
    USER_RENTING_HISTORY("history/user/DTO"), //TODO endpoint name
    INVENTORY("inventory"),
    USER_CURRENTLY_RENTED_INVENTORY("rentedInventory/user");

    private final String path;

    ApiEndpoints(String path) {
        this.path = path;
    }

    public String getPath() {
        return ApiConfig.getUrl() + path;
    }
}
