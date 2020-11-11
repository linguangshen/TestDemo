package com.test.ceshi;
 
import com.google.common.util.concurrent.RateLimiter;
 
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
 
/**
 *有很多个任务，但希望每秒不超过X个，可用此类
 * Created by dukun on 2019/4/23.
 */
public class Demo1 {
 
    public static void main(String[] args) {
 
        //每秒限制两个令牌 限制每秒执行几次   这里设置了0.5s执行一次
        RateLimiter rateLimiter = RateLimiter.create(2.0);
        List<Runnable> tasks = new ArrayList<Runnable>();
        for (int i = 0; i < 10; i++) {
            tasks.add(new UserRequest(i));
        }
        ExecutorService threadPool = Executors.newCachedThreadPool();
        for (Runnable runnable : tasks) {
            //rateLimiter.acquire()为阻塞的
            System.out.println("等待时间：" + rateLimiter.acquire());
            threadPool.execute(runnable);//执行线程池任务
        }
 
    }
    //处理任务
    private static class UserRequest implements Runnable {
        private int id;
 
        public UserRequest(int id) {
            this.id = id;
        }
 
        public void run() {
            System.out.println(id);
        }
    }
}