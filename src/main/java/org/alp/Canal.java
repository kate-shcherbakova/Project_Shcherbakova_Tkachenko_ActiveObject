package org.alp;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Represents a canal (network) for transmitting data from a Capteur to an Afficheur asynchronously
 * Role: Proxy
 */
public class Canal implements ObserverDeCapteurAsync, CapteurAsync {

    private ScheduledExecutorService scheduler;

    private ObserverDeCapteur observer;

    private Capteur capteur;

    /**
     * Asynchronously notifies the observer to update
     * async observer.update
     */
    @Override
    public void update() {
        this.scheduler.schedule(() -> {
            this.observer.update(this);
        }, 1000, TimeUnit.MILLISECONDS);
    }

    /**
     * Attaches a subject and an observer to the canal
     * pattern observer: capteur - subject, afficheur - observer
     *
     * @param capteur  The subject to attach
     * @param observer The observer to attach
     */
    public void attachSubjectToObserver(Capteur capteur, ObserverDeCapteur observer) {
        this.capteur = capteur;
        this.observer = observer;
    }

    /**
     * Detaches the subject and observer from the canal
     */
    public void detachSubjectFromObserver() {
        this.capteur = null;
        this.observer = null;
        this.scheduler = null;
    }

    /**
     * Sets the scheduler for the canal
     *
     * @param scheduler The scheduler to set
     */
    public void setScheduler(ScheduledExecutorService scheduler) {
        this.scheduler = scheduler;
    }

    //-------------------------------------------------------------------------------------
    // Request from Afficheur for a value


    /**
     * Requests a value from the Capteur asynchronously
     *
     * @return A Future object representing the value
     */
    @Override
    public Future<Integer> getValueFromCapteur() {
        return this.scheduler.schedule(() -> this.capteur.getValue(this), 1000, TimeUnit.MILLISECONDS);
    }

    //-------------------------------------------------------------------------------------

    //-------------------------------------------------------------------------------------
    // Get final values for test

    /**
     * Gets the final values received by the observer from the canal
     *
     * @return A list of final values received by the observer
     */
    public List<Integer> getFinalValuesFromAfficheur() {
        return this.observer.getFinalValues();
    }

    //-------------------------------------------------------------------------------------


}
