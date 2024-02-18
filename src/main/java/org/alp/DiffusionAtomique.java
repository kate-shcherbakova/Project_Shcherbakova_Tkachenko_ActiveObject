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
            // canals are receiving the values now
            this.capteur.lock();

            // signal to observer
            this.canalsFromCapteurImpl = this.capteur.getCanalsFromCapteurImpl();
            for (Canal canal : canalsFromCapteurImpl) {
                canal.update();
            }
        }
    }

    @Override
    public void unblockCanal(Canal canal) {
        this.canalsFromCapteurImpl.remove(canal);
        // every canal has received a value
        if (this.canalsFromCapteurImpl.isEmpty()) {
            this.capteur.unlock();
        }
    }

}
