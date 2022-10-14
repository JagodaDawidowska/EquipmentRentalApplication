package com.jdawidowska.equipmentrentalservice.model;

public class RentingRequest {
    private long idUser;
    private long idItem;

    public RentingRequest(long idUser, long idItem) {
        this.idUser = idUser;
        this.idItem = idItem;
    }

    public RentingRequest() {
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public long getIdItem() {
        return idItem;
    }

    public void setIdItem(long idItem) {
        this.idItem = idItem;
    }

    @Override
    public String toString() {
        return "RentingRequest{" +
                "idUser=" + idUser +
                ", idItem=" + idItem +
                '}';
    }
}
