package org.alp;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class CapteurImpl implements Capteur {

    private AlgoDiffusion strategy;
    private int value = 0;

    private boolean isLocked = false;

    private Set<Canal> canals;


    @Override
    public void setStrategy(AlgoDiffusion strategy) {
        this.strategy = strategy;
        // ?? this.strategy.configure(this);
    }

    @Override
    public void tick() {
        if (!this.isLocked) {
            this.value++;
        }
        Logger.getGlobal().info("\nValue during tick() :" + this.value + "\n");
        this.strategy.execute();
    }

    @Override
    public boolean getIsLocked(){
        return this.isLocked;
    }

    @Override
    public Set<Canal> getCanalsFromCapteurImpl() {
        // new copy - don't change previous
        return new HashSet<>(this.canals);
    }


}