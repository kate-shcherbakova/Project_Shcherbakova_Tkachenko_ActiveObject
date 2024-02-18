package org.alp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Logger;

public class Afficheur implements ObserverDeCapteur {

    private final List<Integer> finalValues = new ArrayList<>();

    private String name;

    public Afficheur(String name) {
        this.name = name;
    }

    // Canal transfer a signal to update
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

    @Override
    public List<Integer> getFinalValues() {
        Logger.getGlobal().info("Final values " + this.name + finalValues.toString() + "\n");
        return this.finalValues;
    }
}
