import org.alp.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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


    }

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
    }
}
