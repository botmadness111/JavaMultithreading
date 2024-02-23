import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Runner runner = new Runner();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                runner.firstThread();
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                runner.secondThread();
            }
        });


        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        runner.info();
    }
}

class Runner {
    Account account1 = new Account();
    Account account2 = new Account();

    Lock lock1 = new ReentrantLock();
    Lock lock2 = new ReentrantLock();

    Random random = new Random();

    public void firstThread() {
        for (int i = 0; i < 1000; i++) {
            takeLocks();
            try {
                Account.transfer(account1, account2, random.nextInt(10));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    public void secondThread() {
        for (int i = 0; i < 1000; i++) {
            takeLocks();
            try {
                Account.transfer(account2, account1, random.nextInt(10));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    private void takeLocks() {
        boolean firstLockIsTaken = false;
        boolean secondLockIsTaken = false;

        while (true) {
            try {
                firstLockIsTaken = lock1.tryLock();
                secondLockIsTaken = lock2.tryLock();
            } finally {
                if (firstLockIsTaken && secondLockIsTaken) //если удалось взять первый и второй Lock
                    return;

                if (firstLockIsTaken) {
                    lock1.unlock();
                }

                if (secondLockIsTaken) {
                    lock2.unlock();
                }
            }
        }
    }

    public void info() {
        System.out.println("First Account: " + account1.getCash());
        System.out.println("Second Account: " + account2.getCash());
        System.out.println("Summary Accounts: " + (account1.getCash() + account2.getCash()));
    }
}

class Account {
    private int cash = 10000;

    public static void transfer(Account account1, Account account2, int value) {
        account1.cash -= value;
        account2.cash += value;
    }

    public int getCash() {
        return cash;
    }
}