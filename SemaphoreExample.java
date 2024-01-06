import java.util.concurrent.Semaphore;

public class SemaphoreExample {

    private static int sharedResource = 0;
    private static final Semaphore semaphore = new Semaphore(1);  

    public static void main(String[] args) {
       
        int numThreads = 3;
        for (int i = 1; i <= numThreads; i++) {    // Create and start multiple threads
            Thread thread = new Thread(new IncrementThread("Thread-" + i));
            thread.start();
        }
    }
    private static class IncrementThread implements Runnable {   // Thread class to increment the shared resource using Semaphore
        private final String threadName;

        public IncrementThread(String threadName) {
            this.threadName = threadName;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                try {
                    semaphore.acquire();  // Acquire the semaphore permit before accessing the shared resource
                    sharedResource++;   // Critical section: Increment the shared resource
                    System.out.println(threadName + ": Shared Resource = " + sharedResource);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();  // Release the semaphore permit
                }
                try {
                    Thread.sleep(100);  
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
