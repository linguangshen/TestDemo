package com.test;

import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

//zookeeper唯一id
public class UniqueId implements Watcher {
    //zk地址
    String url ="127.0.0.1:2181";
    //计数器对象
    CountDownLatch countDownLatch =new CountDownLatch(1);
    //用户生成序号的节点
    String defaultPath ="/UniqueId";
    //连接对象
    ZooKeeper zooKeeper;

    @Override
    public void process(WatchedEvent watchedEvent) {
        try {
            //捕获事件
            if (watchedEvent.getType()== Event.EventType.None){
                if (watchedEvent.getState()== Event.KeeperState.SyncConnected){
                    System.out.println("连接成功");
                    countDownLatch.countDown();
                }else if(watchedEvent.getState()== Event.KeeperState.Disconnected){
                    System.out.println("连接断开");
                }else if(watchedEvent.getState()== Event.KeeperState.Expired){
                    System.out.println("连接超时");
                    //超时后服务端已经将连接释放，需要重新连接服务器端
                    zooKeeper =new ZooKeeper(url,6000,new UniqueId());
                }else if(watchedEvent.getState()== Event.KeeperState.AuthFailed){
                    System.out.println("验证失败");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //构造方法
    public UniqueId(){
        try {
            //打开连接
            zooKeeper =new ZooKeeper(url,5000,this);
            //阻塞线程  确保创建成功
            countDownLatch.await();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //生成唯一id的方法
    public String getUniqueId(){
        String path="";
        try {
            //创建一个临时有序节点
            path = zooKeeper.create(defaultPath,new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        }catch (Exception e){
            e.printStackTrace();
        }
        //返回的例子  /UniqueId0000000001
        return path.substring(9);
    }

    public static void main(String[] args) {
        UniqueId uniqueId = new UniqueId();
        for (int i=0;i<=5;i++){
            String uniqueId1 = uniqueId.getUniqueId();
            System.out.println(uniqueId1);
        }
    }
}
