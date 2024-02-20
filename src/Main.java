
public class Main {
    public static void main(String[] args) {
        Thread thread1 = new Thread(new Runner());
        Thread thread2 = new Thread(new Runner());

        thread1.start();
        thread2.start();
    }
}

class Runner implements Runnable{

    @Override
    public void run() {
        for (int i=0; i<100; i++){
            System.out.println(i);
        }
    }
}

