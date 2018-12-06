package com.datagroup.ESLS.netty.handler;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("handler22")
@Slf4j
public class Handler22 implements ServiceHandler{

    @Override
    public byte[]  execute(byte[] header,byte[] message){
        log.info("标签移除-----处理器执行！");
        return message;
    }

    @Override
    public String execute(String message, Channel channel) {
        return null;
    }
}
