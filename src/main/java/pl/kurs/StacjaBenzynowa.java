package pl.kurs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StacjaBenzynowa {
    private Dystrybutor dystrybutor;
    private ExecutorService executorService;

    private final Lock lock = new ReentrantLock();
    // Condition jest powiązany z konkretnym lockiem
    private final Condition jestWolne = lock.newCondition();
    private boolean czyZajety = false;

    public StacjaBenzynowa(Dystrybutor dystrybutor, ExecutorService executorService) {
        this.dystrybutor = dystrybutor;
        this.executorService = executorService;
    }

    public void rozpocznijTankowanie(int liczbaTankowan) {
        for (int i = 0; i < liczbaTankowan; i++) {
            executorService.execute(this::useDystrybutor);
        }
        executorService.shutdown();
    }

    private void useDystrybutor() {
        lock.lock(); // Wchodzimy do obszaru chronionego
        try {
            // Dopóki dystrybutor jest zajęty, wątek "zasypia" na Condition
            while (czyZajety) {
                System.out.println(Thread.currentThread().getName() + " czeka, aż ktoś odjedzie...");
                jestWolne.await(); // Tu wątek puszcza locka i czeka na sygnał
            }

            // Teraz mamy pewność, że jest wolne i mamy locka
            czyZajety = true;
            System.out.println(Thread.currentThread().getName() + " zaczyna tankować.");
            dystrybutor.zatankuj();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            // Po zakończeniu tankowania:
            czyZajety = false;
            jestWolne.signal(); // Budzimy JEDEN z czekających wątków
            lock.unlock();      // Dopiero teraz inny wątek może wejść
        }
    }
}
