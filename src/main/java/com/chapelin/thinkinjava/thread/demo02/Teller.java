package com.chapelin.thinkinjava.thread.demo02;

import java.util.concurrent.TimeUnit;


/**
 * 银行柜台管理员
 */
public class Teller implements Runnable, Comparable<Teller> {

    public static int counter = 0;

    private int id = counter++;

    private int serveCount = 0;

    private boolean serveCustomeLine = true;

    private CustomerLine customers;

    public Teller(CustomerLine customers) {
        this.customers = customers;
    }

    public int getServeCount() {
        return this.serveCount;
    }

    @Override
    public int compareTo(Teller o) {
        return serveCount < o.getServeCount() ? -1 : (serveCount == o.getServeCount() ? 0 : 1);
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                Customer take = customers.take();
                TimeUnit.MILLISECONDS.sleep(take.getServeTime());
                synchronized (this) {
                    System.out.println(this + "服务了一个顾客，当前顾客数：" + customers.size());
                    serveCount++;
                    while (!serveCustomeLine) {
                        wait();
                    }
                }
            }
        } catch (InterruptedException e) {
            System.out.println(this + "银行柜台员中断了...");
        }
        System.out.println(this + "银行柜台员下班了...");
    }

    @Override
    public String toString() {
        return "柜员编号:" + id;
    }

    public synchronized void doSomethingElse() {
        serveCount = 0;
        serveCustomeLine = false;
    }

    public synchronized void serveCustomeLine() {
        if (serveCustomeLine) {
            System.out.println(this + "已经在干活了");
        } else {
            serveCustomeLine = true;
            notifyAll();
        }
    }
}

