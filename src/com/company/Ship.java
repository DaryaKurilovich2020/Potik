package com.company;


public class Ship implements Runnable {
    private int containersToTake;
    private int containersToLeave;
    private final Port port;
    private String name;


    public int getContainersToTake() {
        return containersToTake;
    }

    public int getContainersToLeave() {
        return containersToLeave;
    }

    public Ship(String name, int containersToTake, int containersToLeave, Port port) {
        this.name = name;
        this.containersToTake = containersToTake;
        this.containersToLeave = containersToLeave;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        Integer dock = port.askPermission(this);
        System.out.println(this.name + " попал в порт");
        port.leaveAndTakeContainers(this);
        port.returnPermission(dock);
    }
}