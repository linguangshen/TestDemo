package com.test;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.config.Config;

public class RedissionTest {

    private static RLock rLock;

    static {
        Config config =new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379").setDatabase(0);
        Redisson redisson =(Redisson)Redisson.create(config);
        rLock = redisson.getLock("redis_lock");
    }


    static class stockThread implements Runnable{

        @Override
        public void run() {
            //上锁
            rLock.lock();
            //减少库存
            Stock stock = new Stock();
            int b = stock.reduceStock();

            //解锁
            rLock.unlock();
            System.out.println(b);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            new Thread(new stockThread(),"线程"+i).start();
        }

    }
}
