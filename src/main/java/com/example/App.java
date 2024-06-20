// App.java
package com.example;

import com.example.consumer.Consumer;
import com.example.producer.Producer;
import com.example.queue.BankQueue;

public class App {
    public static void main(String[] args) {
        BankQueue bankQueue = new BankQueue(100); // Example with max queue length 5

        Producer producer = new Producer(bankQueue);

        Thread producerThread = new Thread(producer);
        int numConsumers=3;
    // Creating an array to hold consumer threads
        Consumer[] consumers = new Consumer[numConsumers];
        Thread[] consumerThreads = new Thread[numConsumers];

        // Creating consumer threads
        for (int i = 0; i < numConsumers; i++) {
            consumers[i] = new Consumer(bankQueue);
            consumerThreads[i] = new Thread(consumers[i]);
            consumerThreads[i].start();
        }
        producerThread.start();

    }
}
