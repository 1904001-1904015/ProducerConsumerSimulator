package com.example.queue;

import com.example.GrocerySimulator;
import com.example.model.Customer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

abstract public class Queues  {
    private int totalCustomersArrived = 0;
    private int totalCustomersServed = 0;
    private int totalCustomersLeft = 0;
    private long totalServiceTime = 0;


    public Queues() {

    }

    public synchronized void addCustomer(Customer customer) {
    }

    public synchronized Customer getNextCustomer(int queueIndex) throws InterruptedException {
        return null;
    }

    public synchronized boolean isQueueEmpty(int queueIndex) {
        return false;
    }

    public synchronized boolean isWaitingQueueEmpty() {
        return false;
    }

    public synchronized void customerLeft() {
    }

    public synchronized int getTotalCustomersArrived() {
        return totalCustomersArrived;
    }

    public synchronized int getTotalCustomersServed() {
        return totalCustomersServed;
    }

    public synchronized int getTotalCustomersLeft() {
        return totalCustomersLeft;
    }

    public synchronized double getAverageServiceTime() {
        return totalCustomersServed == 0 ? 0 : (double) totalServiceTime / totalCustomersServed;
    }

    public synchronized void distributeCustomers() throws InterruptedException {

    }
}
