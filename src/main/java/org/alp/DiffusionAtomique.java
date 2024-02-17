package org.alp;

import java.util.HashSet;
import java.util.Set;

public class DiffusionAtomique implements AlgoDiffusion {
    private Capteur capteur;
    private Set<Canal> canalsFromCapteurImpl;

    public DiffusionAtomique() {
    }

    public void configure(Capteur capteur) {
        this.capteur = capteur;
        this.canalsFromCapteurImpl = new HashSet<>();
    }

    @Override
    public void execute() {
        // if capteur is locked - value hasn't changed - no need to update
        if (!this.capteur.getIsLocked()) {
            // ?? this.capteur.lock();

            // signal to observer
            this.canalsFromCapteurImpl = this.capteur.getCanalsFromCapteurImpl();
            for (Canal canal : canalsFromCapteurImpl) {
                canal.update();
            }
        }
    }
}
