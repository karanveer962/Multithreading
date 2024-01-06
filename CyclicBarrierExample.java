import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierExample {
    
    private static final int NUM_THREADS = 3;

    public static void main(String[] args) {
        // Create a CyclicBarrier with the specified number of parties (threads)
        CyclicBarrier cyclicBarrier = new CyclicBarrier(NUM_THREADS, new BarrierAction());

        for (int i = 0; i < NUM_THREADS; i++) {   // Create and start multiple threads
            Thread thread = new Thread(new WorkerThread(cyclicBarrier));
            thread.start();
        }
    }

    private static class BarrierAction implements Runnable {   // Runnable for the barrier action
        @Override
        public void run() {
            System.out.println("Barrier action: All threads reached the barrier.");      // This code is executed when all threads reach the barrier
        }
    }

    private static class WorkerThread implements Runnable {      // Worker thread class
        private final CyclicBarrier cyclicBarrier;

        public WorkerThread(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + ": Performing some work.");
                
                Thread.sleep(1000);  // Simulate some work being done by the thread

                System.out.println(Thread.currentThread().getName() + ": Waiting at the barrier.");

                // Threads will wait at the barrier until all threads reach this point
                cyclicBarrier.await();

                System.out.println(Thread.currentThread().getName() + ": Resumed after the barrier.");

                // Simulate additional work being done after the barrier
                Thread.sleep(500);

                System.out.println(Thread.currentThread().getName() + ": Completed.");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
