package com.project;

// Cada cotxe és una tasca (Runnable) que s'executarà en un fil del pool
public class Car implements Runnable {

    private final String nom;
    private final ParkingLot aparcament;

    public Car(String nom, ParkingLot aparcament) {
        this.nom = nom;
        this.aparcament = aparcament;
    }

    @Override
    public void run() {
        try {
            // El cotxe intenta entrar (pot haver d'esperar si està ple)
            aparcament.entrar(nom);

            // Simulem el temps que el cotxe està aparcat
            Thread.sleep(2000);

            // El cotxe surt i allibera la seva plaça
            aparcament.sortir(nom);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}