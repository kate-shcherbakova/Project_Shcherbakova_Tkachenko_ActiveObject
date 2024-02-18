package org.alp;

import java.util.HashSet;
import java.util.Set;

/**
 * Diffusion atomique strategy for transmitting Capteur (subject) values to Afficheur (observer)
 * Ensures that all observers receive the same value
 */
public class DiffusionAtomique implements AlgoDiffusion {
    private Capteur capteur;
    private Set<Canal> canalsFromCapteurImpl;

    /**
     * Proceeds configuration operations
     *
     * @param capteur The sensor to configure
     */
    public void configure(Capteur capteur) {
        this.capteur = capteur;
        this.canalsFromCapteurImpl = new HashSet<>();
    }

    /**
     * Executes the diffusion atomique
     * Locks the sensor to prevent modification of its value while observers are receiving it
     * Signals observers through canals to update
     */
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

    /**
     * Manages a canal after it has received a value
     * Unlocks the sensor when all canals have received the value
     *
     * @param canal The canal that has received a value
     */
    @Override
    public void manageCanal(Canal canal) {
        this.canalsFromCapteurImpl.remove(canal);
        // every canal has received a value
        if (this.canalsFromCapteurImpl.isEmpty()) {
            this.capteur.unlock();
        }
    }

}
