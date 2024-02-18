package org.alp;

import java.util.HashSet;
import java.util.Set;

/**
 * Diffusion sequentielle strategy for transmitting Capteur (subject) values to Afficheur (observer)
 * Ensures that all observers receive the same value
 * Sequence of receiving the value may vary
 */
public class DiffusionSequentielle implements AlgoDiffusion {
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
     * Executes the diffusion sequentielle
     * Signals observers through canals to update
     */
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

    /**
     * Manages a canal after it has received a value
     *
     * @param canal The canal that has received a value
     */
    @Override
    public void manageCanal(Canal canal) {
        this.canalsFromCapteurImpl.remove(canal);
    }

}
