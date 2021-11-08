package com.company;

import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        ReentrantLock locker = new ReentrantLock();
        Port port = new Port(2, 5000, 1000, locker);
        for (int i = 0; i < 4; i++) {
            Thread thread = new Thread(new Ship("Корабль " + i, 150, 200, port));
            thread.start();
        }

//        System.out.println("Все корабли завершили своё задание");
    }
}