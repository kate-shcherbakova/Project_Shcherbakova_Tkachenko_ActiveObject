package org.alp;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public interface Capteur {

    void setStrategy(AlgoDiffusion strategy);

    void tick();
//    void tick() throws ExecutionException, InterruptedException;

    boolean getIsLocked();

    Set<Canal> getCanalsFromCapteurImpl();

    void attach(Canal canal, ObserverDeCapteur afficheur);

    void detach(Canal canal);

    void lock();

    void unlock();

    int getValue(Canal canal);

    List<List<Integer>> getFinalValuesFromCanals();
}
