package com.chapelin.thinkinjava.thread.demo02;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainDemo {

    private static int MAX_LINE = 50;
    private static int ADJUST_PERIOD = 2000;

    public static void main(String[] args) throws IOException {
        CustomerLine customers = new CustomerLine(MAX_LINE);
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new CustomerGenerator(customers));
        executorService.execute(new TellerManager(customers, executorService, ADJUST_PERIOD));

        System.out.println("按回车键退出...");
        System.in.read();
        executorService.shutdownNow();
//        executorService.shutdown();
    }
}
