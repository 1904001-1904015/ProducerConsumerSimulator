package com.example;

import com.example.consumer.Consumer;
import com.example.distributor.Distributor;
import com.example.producer.Producer;
import com.example.queue.GroceryQueues;

public class App {
    public static void main(String[] args) {
        int numberOfQueues = 3;
        int maxQueueLength = 5;
        int minimumServiceTime = 60;
        int maximumServiceTime = 3000;
        int minimumArrivalTime = 2;
        int maximumArrivalTime = 6;
        double simulationDurationMinutes = 5 / 60.0;

        GroceryQueues groceryQueues = new GroceryQueues(numberOfQueues, maxQueueLength);

        Producer producer = new Producer(groceryQueues, minimumArrivalTime, maximumArrivalTime, minimumServiceTime, maximumServiceTime);
        Thread producerThread = new Thread(producer);
        Consumer[] consumers = new Consumer[numberOfQueues];
        Thread[] consumerThreads = new Thread[numberOfQueues];

        for (int i = 0; i < numberOfQueues; i++) {
            consumers[i] = new Consumer(groceryQueues, i);
            consumerThreads[i] = new Thread(consumers[i]);
            consumerThreads[i].start();
        }

        producerThread.start();

        Distributor distributor = new Distributor(groceryQueues);
        Thread distributorThread = new Thread(distributor);
        distributorThread.start();

        try {
            Thread.sleep((long) (simulationDurationMinutes * 60 * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        producerThread.interrupt();
        distributor.requestShutdown();
        distributorThread.interrupt();
        for (Thread consumerThread : consumerThreads) {
            consumerThread.interrupt();
        }

        try {
            producerThread.join();
            distributorThread.join();
            for (Thread consumerThread : consumerThreads) {
                consumerThread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Simulation completed.");
        System.out.println("Total customers arrived: " + groceryQueues.getTotalCustomersArrived());
        System.out.println("Total customers served: " + groceryQueues.getTotalCustomersServed());
        System.out.println("Total customers left without being served: " + groceryQueues.getTotalCustomersLeft());
        System.out.println("Average service time: " + groceryQueues.getAverageServiceTime());
    }
}
