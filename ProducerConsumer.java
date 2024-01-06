import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumer{

    public static void main(String[] args) {
       
        Queue<Integer> buffer = new LinkedList<>();    // Shared buffer between producer and consumer
        int capacity = 5;

       
        Thread producerThread = new Thread(new Producer(buffer, capacity), "Producer"); // Create and start producer  threads
        Thread consumerThread = new Thread(new Consumer(buffer), "Consumer");  // Create and start and consumer threads

        producerThread.start();
        consumerThread.start();
    }


    private static class Producer implements Runnable {     // Producer class
        private final Queue<Integer> buffer;
        private final int capacity;

        public Producer(Queue<Integer> buffer, int capacity) {
            this.buffer = buffer;
            this.capacity = capacity;
        }

        @Override
        public void run() {
            for (int i = 1; i <= 10; i++) {
                synchronized (buffer) {
                    while (buffer.size() == capacity) { // Wait while the buffer is full
                        try {
                            System.out.println("Buffer is full. Producer is waiting...");
                            buffer.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Producing: " + i);  // Produce an item and add to the buffer
                    buffer.add(i);
                    buffer.notify();     // Notify the consumer that an item is available
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private static class Consumer implements Runnable {   // Consumer class
        private final Queue<Integer> buffer;

        public Consumer(Queue<Integer> buffer) {
            this.buffer = buffer;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (buffer) {
                    while (buffer.isEmpty()) {  // Wait while the buffer is empty
                        try {
                            System.out.println("Buffer is empty. Consumer is waiting...");
                            buffer.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    int item = buffer.poll();  // Consume an item from the buffer
                    System.out.println("Consuming: " + item);
                    buffer.notify();    // Notify the producer that space is available in the buffer
                }

                // Simulate some work being done by the consumer
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
