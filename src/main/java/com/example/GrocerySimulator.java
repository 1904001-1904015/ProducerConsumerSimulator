package com.example;

import com.example.consumer.GroceryConsumer;
import com.example.distributor.Distributor;
import com.example.producer.Producer;
import com.example.queue.GroceryQueues;

public class GrocerySimulator {

    private static final int scale_down = 1000;
    private static final int millisecondsInSecond = 1000/ scale_down;
    private static final int millisecondsInMinute = 60 * millisecondsInSecond;
    private static final int millisecondsInHour = 60 * millisecondsInMinute;


    public static final int numberOfQueues = 3;
    public static final int maxQueueLength = 2;
    public static final int minimumServiceTime = 60*millisecondsInSecond;
    public static final int maximumServiceTime = 300*millisecondsInSecond;
    public static final int minimumArrivalTime = 20*millisecondsInSecond;
    public static final int maximumArrivalTime = 60*millisecondsInSecond;
    public static final double maximum_waiting_time_in_waiting_queue = 10*millisecondsInSecond;
    public static final double simulationDurationMinutes = 2*millisecondsInHour;



    public static void main(String[] args) {
        System.out.println("Starting simulation... " + simulationDurationMinutes);

        GroceryQueues groceryQueues = new GroceryQueues(numberOfQueues, maxQueueLength);

        Producer producer = new Producer(groceryQueues, minimumArrivalTime, maximumArrivalTime, minimumServiceTime, maximumServiceTime);
        Thread producerThread = new Thread(producer);
        GroceryConsumer[] groceryConsumers = new GroceryConsumer[numberOfQueues];
        Thread[] consumerThreads = new Thread[numberOfQueues];
        Distributor distributor = new Distributor(groceryQueues);
        Thread distributorThread = new Thread(distributor);

        distributorThread.start();
        for (int i = 0; i < numberOfQueues; i++) {
            groceryConsumers[i] = new GroceryConsumer(groceryQueues, i, distributorThread);
            consumerThreads[i] = new Thread(groceryConsumers[i]);
            consumerThreads[i].start();
        }

        producerThread.start();

        try {
            Thread.sleep((long) (simulationDurationMinutes));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
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
            Thread.currentThread().interrupt();
        }

        System.out.println("Simulation completed.");
        System.out.println("Total customers arrived: " + groceryQueues.getTotalCustomersArrived());
        System.out.println("Total customers served: " + groceryQueues.getTotalCustomersServed());
        System.out.println("Total customers left without being served: " + groceryQueues.getTotalCustomersLeft());
        System.out.println("Average service time: " + groceryQueues.getAverageServiceTime());
    }
}
