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
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the project
 * Contains test methods to verify the behavior of the CapteurImpl class with different diffusion strategies
 */
public class ProjectTest {

    private CapteurImpl capteur;
    private ScheduledExecutorService scheduler;
    private final int numberOfThreads = 8;

    private final int numberOfCanals = 3;


    /**
     * Initializes the capteur, canals and afficheurs for each test method
     */
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

    /**
     * Test method to verify the behavior with the DiffusionAtomique strategy
     * Schedules a periodic task to call tick() every 0.3 seconds
     * Waits for 5 seconds, cancels the task
     * Checks the values received by each Afficheur
     *
     * @throws InterruptedException if the thread is interrupted while sleeping
     */
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

        // check - all final values are the same for all afficheurs
        for (int i = 1; i < finalValues.size(); i++) {
            assertEquals(finalValues.get(0), finalValues.get(i));
        }
    }

    /**
     * Test method to verify the behavior with the DiffusionSequentielle strategy
     * Schedules a periodic task to call tick() every 0.3 seconds
     * Waits for 5 seconds, cancels the task
     * Checks the values received by each Afficheur
     *
     * @throws InterruptedException if the thread is interrupted while sleeping
     */
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

        // check - all final values are the same for all afficheurs
        for (int i = 1; i < finalValues.size(); i++) {
            assertEquals(finalValues.get(0), finalValues.get(i));
        }
    }
}
