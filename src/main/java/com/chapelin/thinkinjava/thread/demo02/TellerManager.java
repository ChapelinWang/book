package com.chapelin.thinkinjava.thread.demo02;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 柜台经理
 */
public class TellerManager implements Runnable {

    private int adjustPeriod;
    private ExecutorService executorService;
    private CustomerLine customers;
    private PriorityQueue<Teller> workingTellers = new PriorityQueue<>();
    private Queue<Teller> tellersDoOtherThings = new LinkedList<>();


    public TellerManager(CustomerLine customers, ExecutorService executorService, int adjustPeriod) {
        System.out.println("柜台经理开始工作");
        this.customers = customers;
        this.executorService = executorService;
        this.adjustPeriod = adjustPeriod;

        Teller teller = new Teller(customers);
        executorService.execute(teller);
        workingTellers.add(teller);
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                TimeUnit.MILLISECONDS.sleep(adjustPeriod);
                adjustTellerNumber();
            }
        } catch (InterruptedException e) {
            System.out.println("柜台经理中断了...");
        }
        System.out.println("柜台经理下班了...");
    }

    private void adjustTellerNumber() {
        System.out.println("进行柜员调整...");
        if (customers.size() / workingTellers.size() > 2) {
            if (tellersDoOtherThings.size() > 0) {
                Teller teller = tellersDoOtherThings.remove();
                teller.serveCustomeLine();
                workingTellers.offer(teller);
                System.out.println("增加了一个柜员");
                return;
            }
            Teller teller = new Teller(customers);
            executorService.execute(teller);
            workingTellers.offer(teller);
        }
        if (workingTellers.size() > 1 && customers.size() / workingTellers.size() < 2) {
            releaseOneTeller();
        }
        if (customers.size() == 0) {
            System.out.println("没有顾客了...");
            while (workingTellers.size() > 1) {
                releaseOneTeller();
            }
        }
    }

    private void releaseOneTeller() {
        System.out.println("减少了一个柜员");
        Teller teller = workingTellers.poll();
        teller.doSomethingElse();
        tellersDoOtherThings.add(teller);
    }
}
