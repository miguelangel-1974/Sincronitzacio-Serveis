package com.project;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {

        System.out.println("Inici del programa");

        // Aparcament amb capacitat per a 3 cotxes
        ParkingLot aparcament = new ParkingLot(3);

        // Pool de 6 fils: així hi pot haver més cotxes intentant entrar
        // que places lliures, i es veuran cotxes esperant.
        ExecutorService executor = Executors.newFixedThreadPool(6);

        // Creem 6 cotxes i els enviem a l'executor perquè s'executin a la vegada
        for (int i = 1; i <= 6; i++) {
            Car cotxe = new Car("Cotxe " + i, aparcament);
            executor.execute(cotxe);
        }

        // Tanquem l'executor: no accepta tasques noves,
        // però deixa acabar les que ja tenia.
        executor.shutdown();
    }
}