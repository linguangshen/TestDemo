package com.test;

public class TicketSeller2 {
    public void sell(){
        System.out.println("售票开始");
        int sellTime= 5000;
        try {
            Thread.sleep(sellTime);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("售票结束");

    }

    public void sellTicketWithLock()throws Exception{
        MyLock myLock = new MyLock();
        //获取锁
        myLock.acquireLock();
        sell();
        //释放锁
        myLock.releaseLock();
    }

    public static void main(String[] args) throws Exception{
        TicketSeller2 ticketSeller = new TicketSeller2();
        for (int i = 0; i < 10; i++) {
            ticketSeller.sellTicketWithLock();
        }
    }
}
