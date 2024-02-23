import java.util.Random;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future future = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("Starting thread");

                Thread.sleep(1);
                int value = random.nextInt(10);

                if (value <= 5) {
                    throw new Exception("Someone bad happened");
                } else
                    return value;
            }
        });

        executorService.shutdown();

        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException ex) {
            Throwable e = ex.getCause();
            System.out.println(e.getMessage());
        }


    }
}