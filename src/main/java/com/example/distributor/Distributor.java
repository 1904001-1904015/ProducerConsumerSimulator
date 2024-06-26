package com.example.distributor;

import com.example.queue.Queues;

public class Distributor implements Runnable {
    private final Queues groceryQueues;
    private volatile boolean shutdownRequested = false;

    public Distributor(Queues groceryQueues) {
        this.groceryQueues = groceryQueues;
    }

    public void requestShutdown() {
        shutdownRequested = true;
    }

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
    }
}
