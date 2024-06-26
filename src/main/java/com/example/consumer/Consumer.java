package com.example.consumer;

import com.example.model.Customer;
import com.example.queue.GroceryQueues;

public class Consumer implements Runnable {
    private final GroceryQueues groceryQueues;
    private final int queueIndex;
    private volatile boolean shutdownRequested = false;

    public Consumer(GroceryQueues groceryQueues, int queueIndex) {
        this.groceryQueues = groceryQueues;
        this.queueIndex = queueIndex;
    }

    public void requestShutdown() {
        shutdownRequested = true;
    }

    @Override
    public void run() {
        while (!shutdownRequested || !groceryQueues.isQueueEmpty(queueIndex) || !groceryQueues.isWaitingQueueEmpty()) {
            try {
                Customer customer = groceryQueues.getNextCustomer(queueIndex);
                if (customer != null) {
                    Thread.sleep(customer.getServiceTime());
                    customer.setServed(true); // Mark the customer as served
                    System.out.println("============================================================================================================");
                    System.out.println("Customer served: " + customer.getCustomerId() + " by the server: " + (queueIndex + 1));
                } else {

                    Thread.sleep(100); // Short wait to avoid busy waiting
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Acknowledge the interrupt
                shutdownRequested = true; // Set the shutdown flag
            }
        }
        System.out.println("Consumer " + (queueIndex + 1) + " finished processing remaining customers.");
    }
}
