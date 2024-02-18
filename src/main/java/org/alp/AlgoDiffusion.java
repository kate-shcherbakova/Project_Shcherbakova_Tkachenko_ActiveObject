package org.alp;

public interface AlgoDiffusion {

    void configure(Capteur capteur);

    void execute();

    void unblockCanal(Canal canal);
}
