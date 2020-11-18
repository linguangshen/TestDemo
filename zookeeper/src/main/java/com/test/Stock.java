package com.test;

//用来测试redis的bean类
public class Stock {
    //库存数量
    private static int num =1000;

    //减少库存方法
    public int reduceStock(){
        if (num>0){
            num--;
            return num;
        }else {
            return 0;
        }
    }
}
