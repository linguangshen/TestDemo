package com.test;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

//zookeeper连接案例
public class ZookeeperConnection {
    public static void main(String[] args) {
        try {
            //计数器对象
            CountDownLatch countDownLatch =new CountDownLatch(1);
            //参数一 服务器ip  参数二 会话超时时间  毫秒单位 参数三  监视器对象
            ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1", 5000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    if (watchedEvent.getState()==Event.KeeperState.SyncConnected){
                        System.out.println("连接创建成功");
                        countDownLatch.countDown();
                    }
                }
            });
            //主线程阻塞等待连接对象创建成功
            countDownLatch.await();
            //会话编号
            System.out.println(zooKeeper.getSessionId());
            zooKeeper.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
