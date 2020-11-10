package com.test.bean;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MyRabbitListener {

    //监听队列
    @RabbitListener(queues = "queue_callback01")
    public void msg(String msg, Message message, Channel channel) {
        try {
            System.out.println("接收到消息内容为：" + msg);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);//签收 可批量处理
        } catch (Exception e) {
            e.printStackTrace();
            try {
                //1.业务处理异常，拒绝签收,直接丢弃
                //basicNack(消息签收标签，是否批量,是否重回队列)
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
                //2.业务处理异常，拒绝签收,重回队列
                //channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                //3.业务处理异常，拒绝签收-不支持批量
                //basicReject(消息签收标签，是否重回队列)
                //channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}