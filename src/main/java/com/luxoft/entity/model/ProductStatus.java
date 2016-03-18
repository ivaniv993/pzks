package com.luxoft.entity.model;

/**
 * Created by iivaniv on 08.07.2015.
 */
public enum ProductStatus {
    AVALIABLE,
    NOT_AVALIABLE,
    BOOKED,
    BOUGHT;

    public String getProd_status() {
        return prod_status;
    }

    private String prod_status;




}
