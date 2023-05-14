package com.jdawidowska.equipmentrentalservice.api.dto.response;

import java.util.Date;

public class UserRentingHistoryResponse {

    private String itemName;
    private Date rentDate;
    private Date returnDate;
    private String email;

    public UserRentingHistoryResponse() {
    }

    public UserRentingHistoryResponse(String itemName, Date rentDate, Date returnDate, String email) {
        this.itemName = itemName;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.email = email;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Date getRentDate() {
        return rentDate;
    }

    public void setRentDate(Date rentDate) {
        this.rentDate = rentDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserHistoryResponse{" +
                "itemName='" + itemName + '\'' +
                ", rentDate=" + rentDate +
                ", returnDate=" + returnDate +
                ", email='" + email + '\'' +
                '}';
    }
}
