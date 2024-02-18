package org.alp;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class DiffusionSequentielle implements AlgoDiffusion {
    private Capteur capteur;
    private Set<Canal> canalsFromCapteurImpl;

    public void configure(Capteur capteur) {
        this.capteur = capteur;
        this.canalsFromCapteurImpl = new HashSet<>();
    }

    @Override
    public void execute() {
        // signal to observer
        if (this.canalsFromCapteurImpl.isEmpty()) {
            this.canalsFromCapteurImpl = this.capteur.getCanalsFromCapteurImpl();

            for (Canal canal : canalsFromCapteurImpl) {
                canal.update();
            }
        }

    }

    @Override
    public void manageCanal(Canal canal) {
        this.canalsFromCapteurImpl.remove(canal);
    }

}
