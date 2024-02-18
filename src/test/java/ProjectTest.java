import org.alp.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProjectTest {

    private CapteurImpl capteur;
    private ScheduledExecutorService scheduler;
    private final int numberOfThreads = 8;

    private final int numberOfCanals = 3;


    @BeforeEach
    public void init() {
        this.capteur = new CapteurImpl();
        this.scheduler = Executors.newScheduledThreadPool(numberOfThreads);

        Canal canal1 = new Canal();
        Canal canal2 = new Canal();
        Canal canal3 = new Canal();

        Afficheur afficheur1 = new Afficheur("Afficheur1");
        Afficheur afficheur2 = new Afficheur("Afficheur2");
        Afficheur afficheur3 = new Afficheur("Afficheur3");

        canal1.setScheduler(scheduler);
        canal2.setScheduler(scheduler);
        canal3.setScheduler(scheduler);

        this.capteur.attach(canal1, afficheur1);
        this.capteur.attach(canal2, afficheur2);
        this.capteur.attach(canal3, afficheur3);

    }
/*
    @Test
    public void diffusionAtomiqueTest() throws InterruptedException {

        this.capteur.setStrategy(new DiffusionAtomique());

        // the periodic task of calling tick() every 0.3 sec
        Future<?> tickMutator = this.scheduler.scheduleAtFixedRate(() -> {
                    this.capteur.tick();
                },
                0, 300, TimeUnit.MILLISECONDS);

        // wait 5 sec and then cancel the task
        sleep(5000);
        tickMutator.cancel(true);

        // getResults...

        // wait for all tasks to finish during 5 sec, after manually stop them
        this.scheduler.awaitTermination(5, TimeUnit.SECONDS);

        List<List<Integer>> finalValues = this.capteur.getFinalValuesFromCanals();

        Logger.getGlobal().info("\ndiffusionAtomiqueTest result: " + finalValues + "\n");

        /*
        for (int i=0; i< finalValues.size()-1; i++){
            assertEquals(finalValues.get(i), finalValues.get(i + 1));
        } */
//    }


    @Test
    public void diffusionSequentielleTest() throws InterruptedException {
        this.capteur.setStrategy(new DiffusionSequentielle());

        // the periodic task of calling tick() every 0.3 sec
        Future<?> tickMutator = this.scheduler.scheduleAtFixedRate(() -> {
                    this.capteur.tick();
                },
                0, 300, TimeUnit.MILLISECONDS);

        // wait 5 sec and then cancel the task
        sleep(5000);
        tickMutator.cancel(true);

        // getResults...

        // wait for all tasks to finish during 5 sec, after manually stop them
        this.scheduler.awaitTermination(5, TimeUnit.SECONDS);

        List<List<Integer>> finalValues = this.capteur.getFinalValuesFromCanals();

        Logger.getGlobal().info("\ndiffusionSequentielleTest result: " + finalValues + "\n");
    }
}
