package com.jdawidowska.equipmentrentalservice.model;

public class RentedInventory {

    Long id;
    Long idHistory;
    Long idUser;
    Long idItem;
    Integer amount;

    public RentedInventory() {
    }

    public RentedInventory(Long id, Long idHistory, Long idUser, Long idItem, Integer amount) {
        this.id = id;
        this.idHistory = idHistory;
        this.idUser = idUser;
        this.idItem = idItem;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdHistory() {
        return idHistory;
    }

    public void setIdHistory(Long idHistory) {
        this.idHistory = idHistory;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getIdItem() {
        return idItem;
    }

    public void setIdItem(Long idItem) {
        this.idItem = idItem;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "RentedInventory{" +
                "id=" + id +
                ", idHistory=" + idHistory +
                ", idUser=" + idUser +
                ", idItem=" + idItem +
                ", amount=" + amount +
                '}';
    }
}
