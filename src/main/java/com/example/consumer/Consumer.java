package com.example.consumer;

import com.example.model.Customer;
import com.example.queue.BankQueue;
import java.util.UUID;

public class Consumer implements Runnable {
    private final String consumerId; // Randomly generated ID for each consumer
    private final BankQueue bankQueue;

    public Consumer(BankQueue bankQueue) {
        this.consumerId = UUID.randomUUID().toString(); // Generate random UUID as consumer ID
        this.bankQueue = bankQueue;
    }

    @Override
    public void run() {
        while (true) {
            Customer customer = bankQueue.getNextCustomer();

            if (customer != null) {
                System.out.println("Consumer " + consumerId + " is serving customer with service time " + customer.getServiceTime());

                try {
                    // Simulate serving the customer for the specified service time
                    Thread.sleep(customer.getServiceTime() *10L); // Sleep for service time in seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Mark the customer as served
                customer.setServed(true);
            }
        }
    }
}
