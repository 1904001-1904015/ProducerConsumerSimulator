// QueueSimulator.java
package com.example.simulation;

import com.example.consumer.Consumer;
import com.example.producer.Producer;
import com.example.queue.BankQueue;

public class QueueSimulator {
    public static void main(String[] args) {
        BankQueue bankQueue = new BankQueue(5); // Example with max queue length 5

        Producer producer = new Producer(bankQueue);
        Consumer consumer = new Consumer(bankQueue);

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();
    }
}
