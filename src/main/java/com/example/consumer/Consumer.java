package com.example.consumer;

import com.example.model.Customer;
import com.example.queue.GroceryQueues;

public class Consumer implements Runnable {
    private final GroceryQueues groceryQueues;
    private final int queueIndex;
    private boolean shutdownRequested = false;
    private Thread distributorThread;

    public Consumer(GroceryQueues groceryQueues, int queueIndex, Thread distributorThread) {
        this.groceryQueues = groceryQueues;
        this.queueIndex = queueIndex;
        this.distributorThread = distributorThread;
    }

    public void requestShutdown() {
        shutdownRequested = true;
    }

    @Override
    public void run() {
        while (!shutdownRequested || !groceryQueues.isQueueEmpty(queueIndex) || this.distributorThread.isAlive()) {
            try {
                Customer customer = groceryQueues.getNextCustomer(queueIndex);
                if (customer != null) {
                    // Thread.sleep(customer.getServiceTime());
                    // as the Thread might be interupted, that's why Thread.sleep is replaced with the following code
                    long endTime = System.currentTimeMillis()+customer.getServiceTime();
                    while(System.currentTimeMillis()<endTime){
                        // Busy waiting
                    }
                    customer.setServed(true); // Mark the customer as served
                } else {
                    Thread.sleep(100); // Short wait to avoid busy waiting
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Acknowledge the interrupt
                shutdownRequested = true; // Set the shutdown flag
                System.out.println("Consumer "+ (queueIndex+1)+" thread interrupted.");
            }
        }
    }
}
