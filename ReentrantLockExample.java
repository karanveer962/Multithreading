import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample{

    
    private static int sharedResource = 0;
    private static final Lock lock = new ReentrantLock();  // ReentrantLock for synchronization

    public static void main(String[] args) {
        int numThreads = 3;

        // Create and start multiple threads
        for (int i = 1; i <= numThreads; i++) {
            Thread thread = new Thread(new IncrementThread("Thread-" + i));
            thread.start();
        }
    }

    // Thread class to increment the shared resource using ReentrantLock
    private static class IncrementThread implements Runnable {
        private final String threadName;

        public IncrementThread(String threadName) {
            this.threadName = threadName;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                lock.lock();   // Acquire the lock before accessing the shared resource
                try {
                    sharedResource++;
                    System.out.println(threadName + ": Shared Resource = " + sharedResource);
                } finally {
                    lock.unlock();
                }
                try {
                    Thread.sleep(100); // Simulate some work being done by the thread
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
