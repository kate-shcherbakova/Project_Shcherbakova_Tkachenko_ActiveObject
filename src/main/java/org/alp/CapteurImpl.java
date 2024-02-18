package org.alp;

import java.util.*;
import java.util.logging.Logger;

public class CapteurImpl implements Capteur {

    private AlgoDiffusion strategy;
    private int value = 0;

    private boolean isLocked = false;

    private Set<Canal> canals = new HashSet<>();

    @Override
    public void setStrategy(AlgoDiffusion strategy) {
        this.strategy = strategy;
        this.strategy.configure(this);
    }

    @Override
    public void tick() {
        if (!this.isLocked) {
            this.value++;
        }
        Logger.getGlobal().info("\nValue during tick(): " + this.value + "\n");
        this.strategy.execute();
    }

    @Override
    public boolean getIsLocked() {
        return this.isLocked;
    }

    @Override
    public Set<Canal> getCanalsFromCapteurImpl() {
        // new copy - don't change previous
        return new HashSet<>(this.canals);
    }


    // pattern observer: capteur - subject, afficheur - observer
    @Override
    public void attach(Canal canal, ObserverDeCapteur afficheur) {
        canal.attachSubjectToObserver(this, afficheur);
        this.canals.add(canal);
    }

    @Override
    public void detach(Canal canal) {
        canal.detachSubjectFromObserver();
        this.canals.remove(canal);
    }

    @Override
    public void lock() {
        this.isLocked = true;
    }

    @Override
    public void unlock() {
        this.isLocked = false;
    }

    //-------------------------------------------------------------------------------------
    // Request from Afficheur for value

    @Override
    public int getValue(Canal canal) {
        this.strategy.manageCanal(canal);

        return value;
    }


    //-------------------------------------------------------------------------------------

    //-------------------------------------------------------------------------------------
    // Get final values for test

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

