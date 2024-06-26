package com.example.queue;

import com.example.GrocerySimulator;
import com.example.model.Customer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BankQueues extends Queues {
    public final Queue<Customer> waitingQueue;
    private final int maxQueueLength;
    private int totalCustomersArrived = 0;
    private int totalCustomersServed = 0;
    private int totalCustomersLeft = 0;
    private long totalServiceTime = 0;

    public BankQueues( int maxQueueLength) {
         this.maxQueueLength = maxQueueLength;
        this.waitingQueue = new LinkedList<>();
    }

    public synchronized void addCustomer(Customer customer) {
        totalCustomersArrived++;
        waitingQueue.offer(customer);
        notifyAll(); // Notify distributor thread that a new customer has been added
    }

    public synchronized Customer getNextCustomer(int queueIndex) throws InterruptedException {
        while (waitingQueue.isEmpty()) {
            wait(); // Wait until a customer is available
        }

        Customer customer = waitingQueue.poll();
         if (customer != null) {
            totalServiceTime += customer.getServiceTime();
            totalCustomersServed++;
        }
        return customer;
    }

    public synchronized boolean isWaitingQueueEmpty() {
        return waitingQueue.isEmpty();
    }

    public synchronized void customerLeft() {
        totalCustomersLeft++;
        notifyAll(); // Notify all waiting threads that a customer has left
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

}
