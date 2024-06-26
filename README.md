# ProducerConsumerSimulator

## Authors

- Abu Md. Masbah Uddin, ID: 1904001
- MD. Akib Hasan, ID: 1904015

## Overview

This project simulates bank and grocery queue systems where customers arrive and are served by consumers. It includes producers, consumers, a distributor (for grocery), and queue management.

## Components

- **Queues:** Manages customer queues, tracks statistics.
- **Customer:** Represents a customer with arrival and service times.
- **Producer:** Generates and adds customers to the queue.
- **Consumer:** Serves customers from the queue.
- **Distributor (Grocery only):** Distributes customers to service queues.

## Running the Simulations

### Bank Queue Simulation

1. Compile and run the `BankSimulator` class:

```bash
javac -d bin src/com/example/*.java src/com/example/consumer/*.java src/com/example/model/*.java src/com/example/producer/*.java src/com/example/queue/*.java
java -cp bin com.example.BankSimulator
```

### Grocery Queue Simulation

1. Compile and run the `GrocerySimulator` class:

```bash
javac -d bin src/com/example/*.java src/com/example/consumer/*.java src/com/example/distributor/*.java src/com/example/model/*.java src/com/example/producer/*.java src/com/example/queue/*.java
java -cp bin com.example.GrocerySimulator
```

## Simulation Parameters

- Number of Queues
- Maximum Queue Length
- Minimum/Maximum Service Time
- Minimum/Maximum Arrival Time
- Maximum Waiting Time
- Simulation Duration

## Output

- Total customers arrived
- Total customers served
- Total customers left without being served
- Average service time

## Requirements

- Java 8 or higher
