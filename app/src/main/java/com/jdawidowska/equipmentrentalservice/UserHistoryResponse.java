package com.jdawidowska.equipmentrentalservice;
import java.util.Date;

public class UserHistoryResponse {

    private String itemName;
    private Date rentDate;
    private Date returnDate;
    private String email;

    public UserHistoryResponse() {
    }

    public UserHistoryResponse(String itemName, Date rentDate, Date returnDate, String emial) {
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
