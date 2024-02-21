import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        Work work = new Work();

        Long start = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(1);

        for (int k = 0; k < 5; k++) {
            executorService.submit(() -> {
                for (int i = 0; i < 1000; i++) {
                    work.run();
                }
            });
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MICROSECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Long end = System.currentTimeMillis();

        System.out.println(work.getList().size());
        System.out.println(end - start + " ms");
    }
}

class Work implements Runnable {
    private List<Integer> list = new ArrayList<>();

    @Override
    public void run() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        list.add(2);
    }

    public List<Integer> getList() {
        return list;
    }
}



