import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Start thread");
                for (int i = 0; i < 1_000_000_000; i++) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        System.out.println("in catch of InterruptedException");
                        Thread.currentThread().interrupt();
                    }

                    if (Thread.currentThread().isInterrupted()){
                        break;
                    }

                    Math.sin(i);
                }

            }
        });

        thread.start();

        thread.interrupt(); //меняем состояние потока на 'прерванный'
        thread.join();


        System.out.println("Hello");
    }
}
