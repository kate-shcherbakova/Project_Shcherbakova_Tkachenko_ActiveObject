package org.alp;

import java.util.concurrent.ExecutionException;

public interface ObserverDeCapteurAsync {

    void update() throws ExecutionException, InterruptedException;
}
