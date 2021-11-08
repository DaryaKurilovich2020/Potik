package com.company;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;

public class Port {

    private final int containersCapacity;
    private int currentContainersQuantity;
    private Lock locker;

    ConcurrentMap<Integer, Optional<Ship>> ships = new ConcurrentHashMap<>();

    public Lock getLocker() {
        return locker;
    }

    public Port(int dockQty, int containersCapacity, int currentContainersQty, Lock locker) {
        this.locker = locker;
        for (int i = 0; i < dockQty; i++) {
            ships.put(i, Optional.empty());
        }
        this.containersCapacity = containersCapacity;
        this.currentContainersQuantity = currentContainersQty;
    }

    public int getCountCapacity() {
        return getContainersCapacity() - getCurrentContainersQty();
    }

    public int getContainersCapacity() {
        return containersCapacity;
    }

    public int getCurrentContainersQty() {
        return currentContainersQuantity;
    }

    public void leaveAndTakeContainers(Ship ship) {
        locker.lock();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int containersToLeave = ship.getContainersToLeave();
        int containersToTake = ship.getContainersToTake();
        if (containersToLeave > getCountCapacity()) {
            this.currentContainersQuantity = this.containersCapacity;
        } else {
            this.currentContainersQuantity += containersToLeave;
        }
        if (containersToTake > getCountCapacity()) {
            this.currentContainersQuantity = 0;
        } else {
            this.currentContainersQuantity -= containersToTake;
        }
        System.out.println(ship.getName() + " выполнил задание и покидает причал");
        System.out.println("Текущее кол-во контейнеров в порту: " + currentContainersQuantity);
        locker.unlock();
    }


    public Integer askPermission(Ship ship) {
        locker.lock();
        Integer dockToReturn = null;
        while (dockToReturn == null) {
            dockToReturn = searchForDock(ship);
        }
        locker.unlock();
        return dockToReturn;
    }

    public Integer searchForDock(Ship ship) {
        for (Integer dock : ships.keySet()) {
            if (ships.get(dock).equals(Optional.empty())) {
                ships.put(dock, Optional.of(ship));
                return dock;
            }
        }
        return null;
    }

    public void returnPermission(Integer dock) {
        ships.put(dock, Optional.empty());
    }
}