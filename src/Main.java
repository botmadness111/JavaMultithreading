import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {

        Work work = new Work();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    work.produce();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    work.consumer();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        thread1.start();

        thread2.start();
    }
}

class Work {
    public void produce() throws InterruptedException {
        synchronized (this) {
            System.out.println("Produce starting: ");
            this.wait(); //тут поток остановится и тут же он продолжится
            System.out.println("Produce ending: ");
        }
    }

    public void consumer() throws InterruptedException {
        Thread.sleep(1000);
        Scanner scanner = new Scanner(System.in);
        synchronized (this) {
            System.out.println("Press enter for continue ");
            scanner.nextLine();
            this.notify();

            Thread.sleep(5000);
        }
    }
}





