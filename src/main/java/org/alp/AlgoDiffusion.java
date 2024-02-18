package org.alp;

public interface AlgoDiffusion {

    void configure(Capteur capteur);

    void execute();

    void manageCanal(Canal canal);
}
