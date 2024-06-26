package com.example.model;

import java.util.UUID;

public class Customer {
    private final String customerId;
    private final long arrivalTime;
    private final int serviceTime;
    private boolean served;
    private boolean leftUnserved;

    public Customer(int serviceTime) {
        this.arrivalTime = System.currentTimeMillis();
        this.serviceTime = serviceTime;
        this.served = false;
        this.leftUnserved = false;
        this.customerId = UUID.randomUUID().toString();
    }

    public long getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public boolean isServed() {
        return served;
    }

    public void setServed(boolean served) {
        this.served = served;
    }

    public boolean isLeftUnserved() {
        return leftUnserved;
    }

    public void setLeftUnserved(boolean leftUnserved) {
        this.leftUnserved = leftUnserved;
    }

    public String getCustomerId() {
        return customerId;
    }
}
