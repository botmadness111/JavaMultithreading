import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MyThread thread = new MyThread();
        thread.start();

        Scanner scanner = new Scanner(System.in);

        scanner.nextLine();

        thread.shutdown();
    }
}

class MyThread extends Thread {
    public volatile boolean running = true;

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Hello from Thread");
        }
    }

    public void shutdown() {
        running = false;
    }
}

