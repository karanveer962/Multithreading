public class ConcurrentIncrement {

    private static int counter = 0;

    private static final int NUM_THREADS = 5;   // Number of threads to create
     
    public static void main(String[] args) {

        for (int i = 0; i < NUM_THREADS; i++) {
            Thread thread = new IncrementThread("Thread-" + (i + 1));
            thread.start();
        }
    }

    private static class IncrementThread extends Thread {

        public IncrementThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                synchronized (ConcurrentIncrement.class) {
                    counter++;
                    System.out.println(Thread.currentThread().getName() + ": Counter = " + counter);
                }
                try {
                    Thread.sleep(100);   // Simulate some work being done by the thread
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
