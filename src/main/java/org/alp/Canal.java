package org.alp;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Canal implements ObserverDeCapteurAsync, CapteurAsync {

    private ScheduledExecutorService scheduler;

    private ObserverDeCapteur observer;

    private Capteur capteur;


    // async observer.update
    @Override
    public void update() {
        this.scheduler.schedule(() -> {
            this.observer.update(this);
        }, 1000, TimeUnit.MILLISECONDS);
    }

    // pattern observer: capteur - subject, afficheur - observer
    public void attachSubjectToObserver(Capteur capteur, ObserverDeCapteur observer) {
        this.capteur = capteur;
        this.observer = observer;
    }

    public void detachSubjectFromObserver() {
        this.capteur = null;
        this.observer = null;
        this.scheduler = null;
    }

    public void setScheduler(ScheduledExecutorService scheduler) {
        this.scheduler = scheduler;
    }

    //-------------------------------------------------------------------------------------
    // Request from Afficheur for value

    @Override
    public Future<Integer> getValueFromCapteur() {
        return this.scheduler.schedule(() -> this.capteur.getValue(this), 1000, TimeUnit.MILLISECONDS);
    }

    //-------------------------------------------------------------------------------------

    //-------------------------------------------------------------------------------------
    // Get final values for test

    public List<Integer> getFinalValuesFromAfficheur() {
        return this.observer.getFinalValues();
    }

    //-------------------------------------------------------------------------------------


}
