package com.project;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {

        // Conjunt de dades sobre el qual farem els càlculs
        int[] dades = {2, 4, 4, 4, 5, 5, 7, 9};

        // Mapa concurrent on cada tasca guardarà el seu resultat.
        // És segur perquè les tres tasques l'escriuen a la vegada.
        ConcurrentMap<String, Double> resultats = new ConcurrentHashMap<>();

        // CyclicBarrier per a 3 fils. El Runnable que li passem
        // s'executa quan les tres tasques han arribat a la barrera,
        // és a dir, quan tots els càlculs han acabat.
        CyclicBarrier barrera = new CyclicBarrier(3, new Runnable() {
            @Override
            public void run() {
                System.out.println("Suma: " + resultats.get("suma"));
                System.out.println("Mitjana: " + resultats.get("mitjana"));
                System.out.println("Desviacio estandard: " + resultats.get("desviacio"));
            }
        });

        // Executor amb un pool de 3 fils, un per cada tasca
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Tasca 1: calcula la suma
        Runnable tascaSuma = () -> {
            try {
                double suma = 0;
                for (int n : dades) {
                    suma = suma + n;
                }
                resultats.put("suma", suma);
                barrera.await(); // Esperem que les altres tasques acabin
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        };

        // Tasca 2: calcula la mitjana
        Runnable tascaMitjana = () -> {
            try {
                double suma = 0;
                for (int n : dades) {
                    suma = suma + n;
                }
                double mitjana = suma / dades.length;
                resultats.put("mitjana", mitjana);
                barrera.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        };

        // Tasca 3: calcula la desviació estàndard
        Runnable tascaDesviacio = () -> {
            try {
                // Primer necessitem la mitjana
                double suma = 0;
                for (int n : dades) {
                    suma = suma + n;
                }
                double mitjana = suma / dades.length;

                // Després sumem els quadrats de les diferències amb la mitjana
                double sumaQuadrats = 0;
                for (int n : dades) {
                    sumaQuadrats = sumaQuadrats + (n - mitjana) * (n - mitjana);
                }

                double desviacio = Math.sqrt(sumaQuadrats / dades.length);
                resultats.put("desviacio", desviacio);
                barrera.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        };

        // Executem les tasques en paral·lel
        executor.submit(tascaSuma);
        executor.submit(tascaMitjana);
        executor.submit(tascaDesviacio);

        // Tanquem l'executor
        executor.shutdown();
    }
}