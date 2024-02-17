package org.alp;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Afficheur implements ObserverDeCapteur {
    @Override
    public void update(Canal canalSignalToUpdate) {

        // now Afficheur is notified, we need to get value from Capteur through Canal
        Future<Integer> value = canalSignalToUpdate.getValue();

        // ?? results
    }
}