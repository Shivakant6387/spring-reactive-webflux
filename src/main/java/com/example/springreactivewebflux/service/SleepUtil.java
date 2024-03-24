package com.example.springreactivewebflux.service;

public class SleepUtil {
    public static void sleepSecond(int second){
        try {
            Thread.sleep(second* 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
