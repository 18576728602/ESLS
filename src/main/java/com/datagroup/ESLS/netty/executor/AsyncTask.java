package com.datagroup.ESLS.netty.executor;

import com.datagroup.ESLS.netty.handler.ServiceHandler;
import com.datagroup.ESLS.utils.SpringContextUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * @author lenovo
 */
@Slf4j
@Component("AsyncTask")
public class AsyncTask {
    @Async
    public ListenableFuture<String> execute(String name,Channel channel,byte[] header,byte[] message) {
        log.info("开始执行线程-----" + name);
        byte[] response ;
        try {
            ServiceHandler handler = (ServiceHandler) SpringContextUtil.getBean(name);
            response = handler.execute(header,message);
            try {
                channel.writeAndFlush(Unpooled.wrappedBuffer(response));
            } catch (Exception e) {
                System.out.println(e);
            }
            Thread.sleep(1000);
        } catch (Exception e) {}
        return new AsyncResult<>("成功");
    }
    @Async
    public ListenableFuture<String> execute(String name, Channel channel) {
        log.info("开始执行线程-------"+name);
        String response = "";
        try {
            ServiceHandler handler = (ServiceHandler) SpringContextUtil.getBean(name);
            response = handler.execute(name,channel);
            Thread.sleep(1000);
        } catch (Exception e) {}
        return new AsyncResult<>(response);
    }
    @Async
    public ListenableFuture<String> sendMessage(){
        return new AsyncResult<>("成功");
    }
}
