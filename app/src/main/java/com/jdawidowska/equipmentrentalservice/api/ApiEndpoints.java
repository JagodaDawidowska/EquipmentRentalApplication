package com.jdawidowska.equipmentrentalservice.api;

public enum ApiEndpoints {

    FEEDBACK("feedback"),

    INVENTORY("inventory"),
    ADD_INVENTORY("inventory/add"),
    REMOVE_INVENTORY("inventory/remove/"), //param: idItem

    ALL_CURRENTLY_RENTED_INVENTORY("rentedInventory"),
    USER_CURRENTLY_RENTED_INVENTORY("rentedInventory/user/"), //param: idUser

    RENT_ITEM("renting/rent"),
    RETURN_ITEM("renting/return"),

    USERS("users"),

    LOGIN("login"),
    REGISTER("register"),

    USER_RENTING_HISTORY("history/user/"); //param: idUser

    private final String path;

    ApiEndpoints(String path) {
        this.path = path;
    }

    public String getPath() {
        return ApiConfig.getUrl() + path;
    }
}
