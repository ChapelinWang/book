package com.chapelin.thinkinjava.thread.demo01;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 捕获线程中的异常
 */
public class ThreadCatchExample {


    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor(new MyThreadFactory());
        executorService.execute(new ExcepitonThread());
        executorService.shutdown();
    }


}

class ExcepitonThread implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "开始执行任务..");
        throw new RuntimeException("Sorry,运行时期异常");
    }
}


class ExcepitonHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("hello," + t.getName() + "，我 catch 了" + e);
    }
}


class MyThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        System.out.println(this + "开始造线程...");
        Thread t = new Thread(r, "Alice");
        t.setUncaughtExceptionHandler(new ExcepitonHandler());
        return t;
    }
}
