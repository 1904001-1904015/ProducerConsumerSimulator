package com.example.distributor;

import com.example.model.Customer;
import com.example.queue.GroceryQueues;

public class Distributor implements Runnable {
    private final GroceryQueues groceryQueues;
    private volatile boolean shutdownRequested = false;

    public Distributor(GroceryQueues groceryQueues) {
        this.groceryQueues = groceryQueues;
    }

    public void requestShutdown() {
        shutdownRequested = true;
    }

//    @Override
//    public void run() {
//        try {
//            while (!shutdownRequested) {
//                groceryQueues.distributeCustomers();
//            }
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//        System.out.println("Distributor finished processing remaining customers.");
//    }
    @Override
    public void run() {
        while (!shutdownRequested || !groceryQueues.isWaitingQueueEmpty()) {
            try {
                groceryQueues.distributeCustomers();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Acknowledge the interrupt
                shutdownRequested = true; // Set the shutdown flag
            }
        }
        System.out.println("Distributor finished processing remaining customers.");
    }
}
