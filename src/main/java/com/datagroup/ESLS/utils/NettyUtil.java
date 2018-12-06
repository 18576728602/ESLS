package com.datagroup.ESLS.utils;

import com.datagroup.ESLS.netty.client.NettyClient;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class NettyUtil{
    // 单例模式
    @Autowired
    private ExecutorService executorService;
    public String sendMessage(Channel channel, byte[] message) {
        try {
            NettyClient nettyClient = new NettyClient(channel, message);
            Future future = executorService.submit(nettyClient);
            // 等待命令执行时间
            executorService.awaitTermination(Long.parseLong(SpringContextUtil.getCommandTime()), TimeUnit.SECONDS);
//            System.out.println(isFinish + "==========================");
//            //如果没有执行完
//            if (!isFinish) {
//                //线程池执行结束 不在等待线程执行完毕，直接执行下面的代码
//                executorService.shutdownNow();
//            }
            return future.get().toString();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
