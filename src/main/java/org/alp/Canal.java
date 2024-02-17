package org.alp;

import java.util.concurrent.ExecutionException;
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
//  ??      }, randomDelay(500, 1500), TimeUnit.MILLISECONDS);

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

}
