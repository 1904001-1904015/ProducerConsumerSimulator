package com.example.queue;

import com.example.model.Customer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class GroceryQueues {
    private final ArrayList<Queue<Customer>> queues;
    private final Queue<Customer> waitingQueue;
    private final int maxQueueLength;
    private int totalCustomersArrived = 0;
    private int totalCustomersServed = 0;
    private int totalCustomersLeft = 0;
    private long totalServiceTime = 0;

    public GroceryQueues(int numberOfQueues, int maxQueueLength) {
        this.maxQueueLength = maxQueueLength;
        this.queues = new ArrayList<>(numberOfQueues);
        this.waitingQueue = new LinkedList<>();
        for (int i = 0; i < numberOfQueues; i++) {
            queues.add(new LinkedList<>());
        }
    }

    public synchronized void addCustomer(Customer customer) {
        totalCustomersArrived++;
        waitingQueue.offer(customer);
        notifyAll(); // Notify distributor thread that a new customer has been added
    }

    public synchronized Customer getNextCustomer(int queueIndex) throws InterruptedException {
        while (queues.get(queueIndex).isEmpty() && waitingQueue.isEmpty()) {
            wait(); // Wait until a customer is available
        }

        Customer customer = queues.get(queueIndex).poll();
        if (customer != null) {
            totalCustomersServed++;
            totalServiceTime += customer.getServiceTime();
        }
        return customer;
    }

    public synchronized boolean isQueueEmpty(int queueIndex) {
        return queues.get(queueIndex).isEmpty();
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

    public synchronized void distributeCustomers() throws InterruptedException {
        while (true) {
            while (waitingQueue.isEmpty()) {
                wait(); // Wait until a customer is available in the waiting queue
            }

            Customer customer = waitingQueue.poll();
            if (customer != null) {
                int minQueueSize = Integer.MAX_VALUE;
                int chosenQueueIndex = -1;

                for (int i = 0; i < queues.size(); i++) {
                    int currentQueueSize = queues.get(i).size();
                    if (currentQueueSize < minQueueSize) {
                        minQueueSize = currentQueueSize;
                        chosenQueueIndex = i;
                    }
                }

                if (chosenQueueIndex != -1 && minQueueSize < maxQueueLength) {
                    queues.get(chosenQueueIndex).offer(customer);
                    notifyAll(); // Notify all waiting threads that a customer has been moved from the waiting queue
                } else {
                    waitingQueue.offer(customer); // Put back to waiting queue if no space is available
                    break;
                }
            }
        }
    }
}
