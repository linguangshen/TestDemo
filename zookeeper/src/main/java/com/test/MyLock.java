package com.test;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.*;
import java.util.concurrent.CountDownLatch;

//分布式锁
public class MyLock {
    //zk地址
    String url = "127.0.0.1:2181";
    //计数器对象
    CountDownLatch countDownLatch = new CountDownLatch(1);
    //连接对象
    ZooKeeper zooKeeper;

    private static final String Lock_ROOT_PATH = "/Locks";
    private static final String LOCK_NODE_NAME = "Lock_";
    private String lockPath;//临时有序节点的节点名称

    public MyLock(){
        try {
            zooKeeper= new ZooKeeper(url, 5000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    if (watchedEvent.getType()==Event.EventType.None){
                        if (watchedEvent.getState()==Event.KeeperState.SyncConnected){
                            System.out.println("连接创建成功");
                            countDownLatch.countDown();
                        }
                    }
                }
            });
            countDownLatch.await();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //获取锁
    public void acquireLock() throws Exception{
        createLock();

        attempteLock();
    }

    //创建锁节点
    private void createLock ()throws Exception{
        //判断节点是否存在
        Stat stat = zooKeeper.exists(Lock_ROOT_PATH, false);
        if (stat==null){
            zooKeeper.create(Lock_ROOT_PATH,new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        //创建临时有序节点
        lockPath =zooKeeper.create(Lock_ROOT_PATH+"/"+LOCK_NODE_NAME,new byte[0],ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL );
        System.out.println("节点创建成功"+lockPath);
    }

    //监视器对象  监视上一个节点是否被删除
    Watcher watcher =new Watcher() {
        @Override
        public void process(WatchedEvent watchedEvent) {
            if (watchedEvent.getType()==Event.EventType.NodeDeleted){
                synchronized (this){
                    notifyAll();
                }
            }
        }
    };

    //尝试获取锁
    private void attempteLock ()throws Exception{
        //获取lock节点下的所有子节点
        List<String> children = zooKeeper.getChildren(Lock_ROOT_PATH, false);
        //对子节点进行排序
        Collections.sort(children);
        // 节点例子 /Locks/Lock_0000000000   只要后面Lock_0000000000
        int index = children.indexOf(lockPath.substring(Lock_ROOT_PATH.length()+1));

        if (index==0){
            System.out.println("获取锁成功");
            return;
        }else {
            //上一个节点路径
            String path = children.get(index - 1);
            Stat stat = zooKeeper.exists(Lock_ROOT_PATH + "/" + path, watcher);
            if (stat==null){
                attempteLock();
            }else {
                synchronized (watcher){
                    watcher.wait();
                }
                attempteLock();
            }
        }
    }

    //释放锁
    public  void releaseLock ()throws Exception{
        //删除临时有序节点
        zooKeeper.delete(this.lockPath,-1);
        zooKeeper.close();
        System.out.println("锁已经释放"+this.lockPath);
    }

    public static void main(String[] args) {
        try {
            MyLock myLock =new MyLock();
            myLock.createLock();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}