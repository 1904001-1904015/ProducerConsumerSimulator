package com.example.model;

public class Customer {
    private final long arrivalTime; // Changed arrivalTime to long for timestamp
    private final int serviceTime;
    private boolean served;

    public Customer(int serviceTime) {
        this.arrivalTime = System.currentTimeMillis(); // Set arrivalTime to current timestamp
        this.serviceTime = serviceTime;
        this.served = false;
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
}
