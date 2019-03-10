package com.chapelin.thinkinjava.thread.demo02;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 模拟顾客访问
 */
public class CustomerGenerator implements Runnable {

    private CustomerLine customers;
    private Random random = new Random(47);

    public CustomerGenerator(CustomerLine customers) {
        this.customers = customers;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                TimeUnit.MILLISECONDS.sleep(random.nextInt(400));
                customers.put(new Customer(random.nextInt(2000)));
                System.out.println("进来了一个顾客，当前顾客量：" + customers.size());
            }
        } catch (InterruptedException e) {
            System.out.println("顾客生成器线程中断了...");
        }
        System.out.println("顾客不会再来了...");
    }
}
