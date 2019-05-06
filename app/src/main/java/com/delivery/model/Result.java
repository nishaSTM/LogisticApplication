package com.delivery.model;

import java.util.List;

public class Result {

    public enum STATUS {SUCCESS, ERROR}

    private STATUS status;
    private List<DeliveryItem> data;
    private String error;

    public Result(STATUS status, List<DeliveryItem> data, String error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public List<DeliveryItem> getData() {
        return data;
    }

    public void setData(List<DeliveryItem> data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
