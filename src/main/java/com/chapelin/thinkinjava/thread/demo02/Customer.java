package com.chapelin.thinkinjava.thread.demo02;


/**
 *  顾客
 */
public class Customer {
    private final int serveTime;

    public Customer(int serveTime) {
        this.serveTime = serveTime;
    }

    public int getServeTime() {
        return this.serveTime;
    }

    @Override
    public String toString() {
        return "[" + serveTime + "]";
    }
}
