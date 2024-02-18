package org.alp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Logger;

/**
 * Represents an observer that receives updates from a sensor asynchronously
 * Role: Concrete observer
 */
public class Afficheur implements ObserverDeCapteur {

    private final List<Integer> finalValues = new ArrayList<>();

    private String name;

    /**
     * Is used for testing purposes
     *
     * @param name The name of the Afficheur
     */
    public Afficheur(String name) {
        this.name = name;
    }

    /**
     * Updates the Afficheur with the value received from the Canal
     *
     * @param canal The Canal from which the value is received
     */
    @Override
    public void update(Canal canal) {

        // now Afficheur is notified, we need to get value from Capteur through Canal
        Future<Integer> finalValue = canal.getValueFromCapteur();

        try {
            this.finalValues.add(finalValue.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the values received by the Afficheur
     *
     * @return A list of the values received by the Afficheur
     */
    @Override
    public List<Integer> getFinalValues() {
        Logger.getGlobal().info("Final values " + this.name + finalValues + "\n");
        return this.finalValues;
    }
}
