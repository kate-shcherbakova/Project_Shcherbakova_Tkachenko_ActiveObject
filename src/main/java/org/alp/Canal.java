package org.alp;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Canal implements ObserverDeCapteurAsync, CapteurAsync {

    private ScheduledExecutorService scheduler;

    private ObserverDeCapteur observer;

    // async observer.update
    @Override
    public void update() {
        this.scheduler.schedule(() -> {
            this.observer.update(this);
        }, 1000, TimeUnit.MILLISECONDS);
//  ??      }, randomDelay(500, 1500), TimeUnit.MILLISECONDS);

    }
}
