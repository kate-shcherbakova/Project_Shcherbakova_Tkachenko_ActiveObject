package org.alp;

import java.util.Set;
import java.util.concurrent.ExecutionException;

public interface Capteur {

    void setStrategy(AlgoDiffusion strategy);

    void tick();
//    void tick() throws ExecutionException, InterruptedException;

    boolean getIsLocked();

    Set<Canal> getCanalsFromCapteurImpl();
}
