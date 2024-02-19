### SHCHERBAKOVA Kateryna, TKACHENKO Oleksii

### M2 IL, ALP

# Project: Active Object, Observer, Strategy, Proxy

--- 

# Overview

In this project, we implemented a system for data diffusion from a sensor (`CapteurImpl`) to multiple display
units (`Afficheur`) through intermediary channels (`Canal`). Two diffusion strategies, atomic
diffusion (`DiffusionAtomique`) and sequential diffusion (`DiffusionSequentielle`), were implemented to explore
different modes of data transfer. We also employed several design patterns such as
**Active Object**, **Observer**, **Strategy** and **Proxy** to ensure modularity, flexibility, and efficiency in our
system.

---

# Design patterns concept

## 1. Active Object

In the project, the Active Object pattern is utilized through the asynchronous behavior of the `Canal` class. The Active
Object pattern is a concurrency design pattern that decouples method execution from method invocation to enhance
concurrency and responsiveness. In our project, the Active Object pattern is applied through the asynchronous scheduling
of updates and the use of futures for value retrieval, enabling concurrent and responsive behavior in the system.

### Active Object for the asynchronous scheduling of updates

The `Canal` class schedules updates asynchronously using a `ScheduledExecutorService`. When the sensor data changes,
instead of immediately notifying the attached `Afficheur`, the `Canal` schedules the update operation to be executed
after a certain delay. This decouples the update operation from the main thread, allowing the main thread to continue
its execution without waiting for the update to complete. As a result, the system remains responsive and can handle
other tasks concurrently.

![activeobject_update.png](images/activeobject_update.png)

### Active Object for the use of futures for value retrieval

The `Canal` class provides a method `getValueFromCapteur()` that returns a `Future<Integer>`. This future represents the
value that the `Canal` will eventually receive from the `Capteur`. By returning a future, the `Canal` allows the caller
to retrieve the sensor value asynchronously without blocking the main thread. The caller can continue its execution and
retrieve the sensor value from the future when it becomes available, enhancing concurrency and responsiveness.

![activeobject_getvalue.png](images/activeobject_getvalue.png)

## 2. Observer

## 3. Strategy

## 4. Proxy

---


