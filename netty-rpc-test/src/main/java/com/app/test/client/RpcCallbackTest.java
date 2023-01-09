package com.app.test.client;

import com.netty.rpc.client.handler.AsyncRPCCallback;
import com.netty.rpc.client.handler.RpcFuture;
import com.netty.rpc.client.RpcClient;
import com.netty.rpc.client.proxy.RpcService;
import com.app.test.service.Person;
import com.app.test.service.PersonService;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by luxiaoxun on 2016/3/17.
 */
public class RpcCallbackTest {
    public static void main(String[] args) {
        final RpcClient rpcClient = new RpcClient("192.168.102.128:2181");
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        try {
            RpcService client = rpcClient.createAsyncService(PersonService.class, "");
            int num = 5;
            RpcFuture helloPersonFuture = client.call("callPerson", "Jerry", num);
            helloPersonFuture.addCallback(new AsyncRPCCallback() {
                /**
                 * 正常情况，拿到返回结果
                 * @param result
                 */
                @Override
                public void success(Object result) {
                    List<Person> persons = (List<Person>) result;
                    for (int i = 0; i < persons.size(); ++i) {
                        System.out.println(persons.get(i));
                    }
                    countDownLatch.countDown();
                }

                /**
                 * 异常情况，打印异常日志
                 * @param e
                 */
                @Override
                public void fail(Exception e) {
                    e.printStackTrace();
                    countDownLatch.countDown();
                }
            });

        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        rpcClient.stop();

        System.out.println("End");
    }
}
