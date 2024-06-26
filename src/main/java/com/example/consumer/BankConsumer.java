package com.example.consumer;

import com.example.model.Customer;
import com.example.queue.Queues;

public class BankConsumer implements Runnable {
    private final Queues bankQueues;
    private final int consumer_id;
    private boolean shutdownRequested = false;

    public BankConsumer(Queues bankQueues, int queueIndex) {
        this.bankQueues = bankQueues;
        this.consumer_id = queueIndex;
    }


    @Override
    public void run() {
        while (!shutdownRequested) {
            try {
                Customer customer = bankQueues.getNextCustomer(consumer_id);
                if (customer != null) {
                    // Thread.sleep(customer.getServiceTime());
                    // as the Thread might be interupted, that's why Thread.sleep is replaced with the following code
                    long endTime = System.currentTimeMillis() + customer.getServiceTime();
                    while (System.currentTimeMillis() < endTime) {
                        // Busy waiting
                    }
                    customer.setServed(true); // Mark the customer as served
                    System.out.println("Customer served: " + customer.getCustomerId() + " at server " + (consumer_id + 1));
                } else {
                    Thread.sleep(100); // Short wait to avoid busy waiting
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Acknowledge the interrupt
                shutdownRequested = true; // Set the shutdown flag
                System.out.println("Consumer " + (consumer_id + 1) + " thread interrupted.");
            }
        }
    }
}
