package com.example;

import com.example.consumer.BankConsumer;
import com.example.producer.Producer;
import com.example.queue.BankQueues;

public class BankSimulator {

    public static final int numberOfQueues = 3;
    public static final int maxQueueLength = 5;
    private static final int scale_down = 1000;
    private static final int millisecondsInSecond = 1000 / scale_down;
    private static final int millisecondsInMinute = 60 * millisecondsInSecond;
    private static final int millisecondsInHour = 60 * millisecondsInMinute;
    public static final double simulationDurationMinutes = 2 * millisecondsInHour;
    public static final int minimumServiceTime = 60 * millisecondsInSecond;
    public static final int maximumServiceTime = 300 * millisecondsInSecond;
    public static final int minimumArrivalTime = 20 * millisecondsInSecond;
    public static final int maximumArrivalTime = 60 * millisecondsInSecond;
    public static final double maximum_waiting_time_in_waiting_queue = 10 * millisecondsInSecond;

    public static void main(String[] args) {
        System.out.println("Starting simulation... " + simulationDurationMinutes);

        BankQueues groceryQueues = new BankQueues(maxQueueLength);

        Producer producer = new Producer(groceryQueues, minimumArrivalTime, maximumArrivalTime, minimumServiceTime, maximumServiceTime);
        Thread producerThread = new Thread(producer);
        BankConsumer[] groceryConsumers = new BankConsumer[numberOfQueues];
        Thread[] consumerThreads = new Thread[numberOfQueues];

        for (int i = 0; i < numberOfQueues; i++) {
            groceryConsumers[i] = new BankConsumer(groceryQueues, i);
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
        for (Thread consumerThread : consumerThreads) {
            consumerThread.interrupt();
        }

        try {
            producerThread.join();
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
