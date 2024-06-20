// BankQueue.java
package com.example.queue;

import com.example.model.Customer;

import java.util.LinkedList;
import java.util.Queue;

public class BankQueue {
    private final int maxQueueLength;
    private final Queue<Customer> queue;

    public BankQueue(int maxQueueLength) {
        this.maxQueueLength = maxQueueLength;
        this.queue = new LinkedList<>();
    }

    public synchronized boolean addCustomer(Customer customer) {
        if (queue.size() < maxQueueLength) {
            queue.offer(customer);
            return true;
        }
        return false;
    }

    public synchronized Customer getNextCustomer() {
        return queue.poll();
    }

    public synchronized int getQueueSize() {
        return queue.size();
    }
}
