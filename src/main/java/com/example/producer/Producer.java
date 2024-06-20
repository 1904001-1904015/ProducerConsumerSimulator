package com.example.producer;

import com.example.model.Customer;
import com.example.queue.BankQueue;

import java.util.Random;

public class Producer implements Runnable {
    private final BankQueue bankQueue;
    private final Random random;

    public Producer(BankQueue bankQueue) {
        this.bankQueue = bankQueue;
        this.random = new Random();
    }

    @Override
    public void run() {
        while (true) {
            int arrivalTime = getPoissonRandom(30); // Mean inter-arrival time of 30 seconds (adjust as needed)
            int serviceTime = random.nextInt(241) + 60;

            Customer customer = new Customer(serviceTime);
            boolean added = bankQueue.addCustomer(customer);
            System.out.println("Time: " + System.currentTimeMillis() + " Queue size: " + bankQueue.getQueueSize());
            if (added) {
                System.out.println("Customer arrived at time " + arrivalTime + " with service time " + serviceTime);
            } else {
                System.out.println("Customer arrived at time " + arrivalTime + " but queue is full.");
            }

            try {
                Thread.sleep(arrivalTime * 1000L); // Sleep for arrival time in milliseconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private int getPoissonRandom(double mean) {
        double lambda = 1.0 / mean;  // Calculate λ
        double p = 1.0;              // Initialize p
        int k = 0;                   // Initialize k (number of events)
        Random r = new Random();     // Random number generator
        double L = Math.exp(-lambda); // Calculate e^(-λ)

        do {
            k++;                      // Increment k
            p *= r.nextDouble();     // Generate a random number in (0, 1) and update p
        } while (p > L);             // Continue until p <= e^(-λ)

        return k - 1;                // Return the number of events - 1
    }

}
