package com.test.ceshi;

import com.google.common.util.concurrent.RateLimiter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RateLimiterTest {
//    public static void main(String[] args) throws InterruptedException {
//        //新建一个每秒限制3个的令牌桶
//        RateLimiter rateLimiter = RateLimiter.create(3.0);
//        ExecutorService executor = Executors.newFixedThreadPool(100);
//        for (int i = 0; i < 10; i++) {
//            executor.execute(new Runnable() {
//                @Override
//                public void run() {
//                    //获取令牌桶中一个令牌，最多等待10秒
//                    if (rateLimiter.tryAcquire(1, 10, TimeUnit.SECONDS)) {
//                        System.out.println(Thread.currentThread().getName()+" "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//                    }
//                }
//            });
//        }
//
//        executor.shutdown();
//    }


    public static void main(String[] args) throws InterruptedException {
        //每1s产生0.5个令牌，也就是说该接口2s只允许调用1次
        RateLimiter rateLimiter = RateLimiter.create(0.5,1,TimeUnit.SECONDS);
        ExecutorService executor = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 10; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    //获取令牌桶中一个令牌，最多等待10秒
                    if (rateLimiter.tryAcquire(1, 10, TimeUnit.SECONDS)) {
                        System.out.println(Thread.currentThread().getName()+" "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    }else {
                        System.out.println("请求频繁");
                    }
                }
            });
        }

        executor.shutdown();
    }
}