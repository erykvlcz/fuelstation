package pl.kurs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StacjaRunner {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        StacjaBenzynowa stacjaBenzynowa = new StacjaBenzynowa(new Dystrybutor("Diesel"), executorService);

        stacjaBenzynowa.rozpocznijTankowanie(4);
    }
}
