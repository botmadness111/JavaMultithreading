import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        ProducerConsumer producerConsumer = new ProducerConsumer();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    producerConsumer.produce();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    producerConsumer.consume();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        thread1.start();
        thread2.start();

    }
}

class ProducerConsumer {
    Queue<Integer> queue = new LinkedList<>();
    final int size = 10;
    Object lock = new Object();

    public void produce() throws InterruptedException {

            synchronized (lock) {
                while (true) {

                if (queue.size() == size) {
                    lock.wait();

                }
                queue.add(2);
                lock.notify();
            }
        }
    }

    public void consume() throws InterruptedException {
        while (true) {
            synchronized (lock) {

                if (queue.size() == 0) {
                    lock.wait();
                }

                queue.poll();
                System.out.println(queue.size());

                lock.notify();
            }
            Thread.sleep(1000);
        }
    }
}