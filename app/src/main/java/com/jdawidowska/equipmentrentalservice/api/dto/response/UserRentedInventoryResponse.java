package com.jdawidowska.equipmentrentalservice.api.dto.response;

public class UserRentedInventoryResponse {

    private Long id;
    private String name;
    private Integer amount;

    public UserRentedInventoryResponse() {
    }

    public UserRentedInventoryResponse(Long id, String name, Integer amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ReturnUserResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                '}';
    }
}
