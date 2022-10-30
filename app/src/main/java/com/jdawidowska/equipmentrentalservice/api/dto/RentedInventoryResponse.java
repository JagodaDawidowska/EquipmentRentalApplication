package com.jdawidowska.equipmentrentalservice.api.dto;

public class RentedInventoryResponse {

    private String name;
    private String surname;
    private String equipment;
    private Integer amount;

    public RentedInventoryResponse() {
    }

    public RentedInventoryResponse(String name, String surname, String equipment, Integer amount) {
        this.name = name;
        this.surname = surname;
        this.equipment = equipment;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "RentedInventoryResponse{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", equipment='" + equipment + '\'' +
                ", amount=" + amount +
                '}';
    }
}
