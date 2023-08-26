import java.util.LinkedList;
import java.util.Scanner;

public class PCP {
    private LinkedList<Integer> buffer;
    private int bufferSize;
    private int valueToProduce;
    private int totalItemsProduced;
    private final int productionLimit;
    private final Object lock;

    public PCP(int bufferSize, int productionLimit) {
        this.buffer = new LinkedList<>();
        this.bufferSize = bufferSize;
        this.valueToProduce = 1;
        this.totalItemsProduced = 0;
        this.productionLimit = productionLimit;
        this.lock = new Object();
    }

    public void produce() {
        while (true) {
            synchronized (lock) {
                try {
                    while (buffer.size() == bufferSize || totalItemsProduced >= productionLimit) {
                        if (buffer.size() == bufferSize) {
                            System.out.println("Buffer is full. Producer is waiting...");
                        } else if (totalItemsProduced >= productionLimit) {
                            System.out.println("Production limit reached. Producer is done producing.");
                            lock.notifyAll(); // Notify consumers that producer is done
                            return;
                        }
                        lock.wait(); // Wait if the buffer is full or production limit is reached
                    }
                    buffer.add(valueToProduce);
                    System.out.println("Producer produced: " + valueToProduce);
                    valueToProduce++;
                    totalItemsProduced++;
                    lock.notifyAll(); // Notify the consumer that data is available
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void consume() {
        while (true) {
            synchronized (lock) {
                try {
                    while (buffer.isEmpty()) {
                        lock.wait(); // Wait if the buffer is empty
                    }
                    int consumedValue = buffer.removeFirst();
                    System.out.println("Consumer consumed: " + consumedValue);
                    lock.notifyAll(); // Notify the producer that space is available
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter buffer size: ");
        int bufferSize = scanner.nextInt();

        System.out.print("Enter production limit: ");
        int productionLimit = scanner.nextInt();

        scanner.close();

        PCP processor = new PCP(bufferSize, productionLimit);

        // Create producer and consumer threads
        Thread producerThread = new Thread(() -> processor.produce());
        Thread consumerThread = new Thread(() -> processor.consume());

        // Start the threads
        producerThread.start();
        consumerThread.start();

        try {
            // Wait for producer thread to finish
            producerThread.join();
            // Wait for consumer thread to finish
            consumerThread.join();
            // Print the end of process message
            System.out.println("End of process.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
