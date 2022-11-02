package com.jdawidowska.equipmentrentalservice.api;

public enum ApiEndpoints {

    RENT_ITEM("renting/rent"),
    RETURN_ITEM("renting/return"),
    USER_RENTING_HISTORY("history/user/DTO"), //TODO endpoint name
    INVENTORY("inventory"),
    USERS("users"),
    ADD_INVENTORY("inventory/add"),
    REMOVE_INVENTORY("inventory/remove"),
    FEEDBACK("feedback/findFeedbackResponseDTO"),
    USER_CURRENTLY_RENTED_INVENTORY("rentedInventory/user"),
    ALL_CURRENTLY_RENTED_INVENTORY("rentedInventory/rentedInventoryResponse");

    private final String path;

    ApiEndpoints(String path) {
        this.path = path;
    }

    public String getPath() {
        return ApiConfig.getUrl() + path;
    }
}
