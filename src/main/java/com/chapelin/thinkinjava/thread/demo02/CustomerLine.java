package com.chapelin.thinkinjava.thread.demo02;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 顾客排的队列
 */
public class CustomerLine extends ArrayBlockingQueue<Customer> {

    public CustomerLine(int maxCustomerLine) {
        super(maxCustomerLine);
    }

    @Override
    public String toString() {
        if (this.size() == 0) {
            return "Empty";
        }
        StringBuilder sb = new StringBuilder();
        for (Customer customer : this) {
            sb.append(customer);
        }
        return sb.toString();
    }
}
