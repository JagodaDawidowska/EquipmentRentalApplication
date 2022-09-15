package com.jdawidowska.equipmentrentalservice.model;

public class Inventory {
    Long id;
    String itemName;
    Integer totalAmount;
    Integer availableAmount;

    public Inventory(Long id, String itemName, Integer totalAmount, Integer availableAmount) {
        this.id = id;
        this.itemName = itemName;
        this.totalAmount = totalAmount;
        this.availableAmount = availableAmount;
    }

    public Inventory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return "Inventory{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", totalAmount=" + totalAmount +
                ", availableAmount=" + availableAmount +
                '}';
    }
}
