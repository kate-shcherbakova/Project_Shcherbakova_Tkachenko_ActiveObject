package org.alp;

import java.util.List;

public interface ObserverDeCapteur {

    void update(Canal canal);

    List<Integer> getFinalValues();
}
