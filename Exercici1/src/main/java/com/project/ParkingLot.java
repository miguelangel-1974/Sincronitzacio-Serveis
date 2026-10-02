package com.project;

import java.util.concurrent.Semaphore;

public class ParkingLot {

    // El semàfor controla quantes places lliures queden.
    // Cada permís del semàfor representa una plaça de l'aparcament.
    private final Semaphore semafor;

    // Creem l'aparcament amb una capacitat (nombre de places)
    public ParkingLot(int capacitat) {
        // El nombre de permisos és igual a la capacitat de l'aparcament
        this.semafor = new Semaphore(capacitat);
    }

    // Un cotxe vol entrar a l'aparcament
    public void entrar(String nomCotxe) throws InterruptedException {
        // Si no queden permisos, l'aparcament està ple i el cotxe haurà d'esperar
        if (semafor.availablePermits() == 0) {
            System.out.println(nomCotxe + " espera: l'aparcament esta ple...");
        }

        // acquire() agafa un permís. Si n'hi ha 0, el fil es queda bloquejat
        // fins que un altre cotxe surti i alliberi un permís.
        semafor.acquire();
        System.out.println(nomCotxe + " ha entrat a l'aparcament. Places lliures: "
                + semafor.availablePermits());
    }

    // Un cotxe surt de l'aparcament
    public void sortir(String nomCotxe) {
        // release() allibera un permís, i així un cotxe que espera pot entrar
        semafor.release();
        System.out.println(nomCotxe + " ha sortit de l'aparcament. Places lliures: "
                + semafor.availablePermits());
    }
}