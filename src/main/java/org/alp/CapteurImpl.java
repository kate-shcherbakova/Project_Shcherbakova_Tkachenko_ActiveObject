package org.alp;

import java.util.*;

/**
 * Implementation of the Capteur which represents a sensor which diffuses data to Afficheurs
 * Role: Client
 */
public class CapteurImpl implements Capteur {

    private AlgoDiffusion strategy;

    /**
     * Current sensor value
     */
    private int value = 0;

    private boolean isLocked = false;

    /**
     * Representation of the network over which the values will be transmitted to the Afficheurs
     */
    private Set<Canal> canals = new HashSet<>();

    /**
     * Sets the strategy to transmit the values
     *
     * @param strategy
     */
    @Override
    public void setStrategy(AlgoDiffusion strategy) {
        this.strategy = strategy;
        this.strategy.configure(this);
    }

    /**
     * Increments the sensor value and executes a value transfer using a diffusion strategy
     */
    @Override
    public void tick() {
        if (!this.isLocked) {
            this.value++;
        }
        this.strategy.execute();
    }

    /**
     * Returns whether the sensor is locked
     *
     * @return True if the sensor is locked, false otherwise
     */
    @Override
    public boolean getIsLocked() {
        return this.isLocked;
    }

    /**
     * Returns a copy of the set of canals connected to the sensor
     *
     * @return Copy of canals
     */
    @Override
    public Set<Canal> getCanalsFromCapteurImpl() {
        // new copy - don't change previous
        return new HashSet<>(this.canals);
    }

    /**
     * Attaches a canal and an afficheur to the sensor
     * pattern observer: capteur - subject, afficheur - observer
     *
     * @param canal     The Canal to attach
     * @param afficheur The Afficheur to attach
     */
    @Override
    public void attach(Canal canal, ObserverDeCapteur afficheur) {
        canal.attachSubjectToObserver(this, afficheur);
        this.canals.add(canal);
    }

    /**
     * Detaches a canal from the sensor
     *
     * @param canal The canal to detach
     */
    @Override
    public void detach(Canal canal) {
        canal.detachSubjectFromObserver();
        this.canals.remove(canal);
    }

    /**
     * Locks the sensor
     * Is used for the diffusion atomique strategy
     */
    @Override
    public void lock() {
        this.isLocked = true;
    }

    /**
     * Unlocks the sensor
     * Is used for the diffusion atomique strategy
     */
    @Override
    public void unlock() {
        this.isLocked = false;
    }

    //-------------------------------------------------------------------------------------
    // Request from Afficheur for a value

    /**
     * Returns the sensor value for a canal
     *
     * @param canal The Canal for which to get the sensor value
     * @return The value of the Capteur
     */
    @Override
    public int getValue(Canal canal) {
        this.strategy.manageCanal(canal);

        return value;
    }


    //-------------------------------------------------------------------------------------

    //-------------------------------------------------------------------------------------
    // Request from Test for the values

    /**
     * Gets final values from canal's Afficheur for a test case
     *
     * @return A list of lists of values received by an Afficheur
     */
    @Override
    public List<List<Integer>> getFinalValuesFromCanals() {
        List<List<Integer>> finalValues = new ArrayList<>();

        for (Canal canal : this.canals) {
            finalValues.add(canal.getFinalValuesFromAfficheur());
        }
        return finalValues;
    }

    //-------------------------------------------------------------------------------------

}

