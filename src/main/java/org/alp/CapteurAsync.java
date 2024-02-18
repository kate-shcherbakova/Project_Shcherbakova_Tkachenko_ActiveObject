package org.alp;

import java.util.concurrent.Future;

public interface CapteurAsync {
    Future<Integer> getValueFromCapteur();
}
