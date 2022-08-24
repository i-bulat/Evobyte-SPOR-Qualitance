package com.evo.qualitanceProject.model;

public enum OrderStatusEnum {

    //waiting to be placed
    IN_BASKET("IN_BASKET"),

    //paid and waiting to be delivered
    PLACED("PLACED"),

    //order delivered
    COMPLETED("COMPLETED");

    private String code;

    private OrderStatusEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
