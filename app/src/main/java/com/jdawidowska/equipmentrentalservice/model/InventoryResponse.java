package com.jdawidowska.equipmentrentalservice.model;

public class InventoryResponse {
    private String itemName;
    private Integer totalAmount;
    private Integer availableAmount;


    public InventoryResponse(String itemName, Integer totalAmount, Integer availableAmount, Long idUser, Long idItem) {
        this.itemName = itemName;
        this.totalAmount = totalAmount;
        this.availableAmount = availableAmount;

    }

    public InventoryResponse() {
    }

    public InventoryResponse(String itemName, Integer totalAmount, Integer availableAmount) {
        this.itemName = itemName;
        this.totalAmount = totalAmount;
        this.availableAmount = availableAmount;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(Integer availableAmount) {
        this.availableAmount = availableAmount;
    }

    @Override
    public String toString() {
        return "InventoryResponse{" +
                "itemName='" + itemName + '\'' +
                ", totalAmount=" + totalAmount +
                ", availableAmount=" + availableAmount +
                '}';
    }
}
